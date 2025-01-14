import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.test.context.ActiveProfiles
import product.config.RedisConfig
import product.demo.ProductApplication
import product.dto.request.ProductRequestDto
import product.dto.shared.CustomException
import product.jpa.entity.Product
import product.jpa.repo.BrandRepository
import product.jpa.repo.ProductRepository
import product.service.ProductService
import java.sql.Timestamp
import java.time.LocalDateTime

@SpringBootTest(classes = [ProductApplication::class])
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductServiceTest(
    @Autowired val productService: ProductService,
    @Autowired val brandRepository: BrandRepository,
    @Autowired val redisConfig: RedisConfig
) {

    @SpyBean
    private lateinit var productRepository: ProductRepository

    @Test
    fun testBrandCache() {
        // 캐시를 사용하는 것이 상품쪽이라 여기서 테스트합니다.
        val brands = productService.fetchBrandCache()
        assertNotNull(brands)
        assertEquals(9, brands.size)
        // 전체 다 들어가있는지 확인
        assertTrue(brands.map { it.brandCode }.containsAll(brandRepository.findAll().map { brand -> brand.brandCode }))
    }

    @Test
    fun testGetLowestPriceProducts() {
        // 에러 테스트(상품 등록 전)
        doReturn(emptyList<Product>()).`when`(productRepository).findLowestPriceProductsByCategoryCodes()
        val exception1 = assertThrows(CustomException::class.java) {
            productService.getLowestPriceProducts()
        }
        assertEquals("등록된 상품이 없습니다.", exception1.message)

        // 정상건 mocking
        val mockProductList = mutableListOf<Product>(
            Product().apply { id = 1; productNo = "A_TOP_1"; productName = "A상의1"; brandCode = "A"; categoryCode = "TOP"; price = 11200; createdAt = Timestamp.valueOf(LocalDateTime.parse("2025-01-11T21:54:46.006694")); createdBy = "test" },
            Product().apply { id = 2; productNo = "G_OUTER_1"; productName = "G아우터1"; brandCode = "G"; categoryCode = "OUTER"; price = 5500; createdAt = Timestamp.valueOf(LocalDateTime.parse("2025-01-11T21:54:46.007009")); createdBy = "test" },
            Product().apply { id = 3; productNo = "A_PANTS_1"; productName = "A바지1"; brandCode = "A"; categoryCode = "PANTS"; price = 4200; createdAt = Timestamp.valueOf(LocalDateTime.parse("2025-01-11T21:54:46.007209")); createdBy = "test" },
            Product().apply { id = 4; productNo = "B_PANTS_1"; productName = "B바지1"; brandCode = "B"; categoryCode = "PANTS"; price = 4400; createdAt = Timestamp.valueOf(LocalDateTime.parse("2025-01-11T21:54:46.007209")); createdBy = "test" },
            Product().apply { id = 5; productNo = "E_SNEAKERS_1"; productName = "E스니커즈1"; brandCode = "E"; categoryCode = "SNEAKERS"; price = 9000; createdAt = Timestamp.valueOf(LocalDateTime.parse("2025-01-11T21:54:46.007405")); createdBy = "test" },
            Product().apply { id = 6; productNo = "D_BAG_1"; productName = "D가방1"; brandCode = "D"; categoryCode = "BAG"; price = 2000; createdAt = Timestamp.valueOf(LocalDateTime.parse("2025-01-11T21:54:46.007597")); createdBy = "test" },
            Product().apply { id = 7; productNo = "H_BAG_1"; productName = "H가방1"; brandCode = "H"; categoryCode = "BAG"; price = 1900; createdAt = Timestamp.valueOf(LocalDateTime.parse("2025-01-11T21:54:46.007597")); createdBy = "test" },
            Product().apply { id = 8; productNo = "I_HAT_1"; productName = "I모자1"; brandCode = "I"; categoryCode = "HAT"; price = 1700; createdAt = Timestamp.valueOf(LocalDateTime.parse("2025-01-11T21:54:46.007849")); createdBy = "test" },
            Product().apply { id = 9; productNo = "C_SOCKS_1"; productName = "A양말1"; brandCode = "C"; categoryCode = "SOCKS"; price = 1800; createdAt = Timestamp.valueOf(LocalDateTime.parse("2025-01-11T21:54:46.008053")); createdBy = "test" },
            Product().apply { id = 10; productNo = "H_ACCESSORY_1"; productName = "H액세서리1"; brandCode = "H"; categoryCode = "ACCESSORY"; price = 2300; createdAt = Timestamp.valueOf(LocalDateTime.parse("2025-01-11T21:54:46.008256")); createdBy = "test" },
        )

        doReturn(mockProductList).`when`(productRepository).findLowestPriceProductsByCategoryCodes()

        val responseDto = productService.getLowestPriceProducts()
        assertNotNull(responseDto.detailList)
        assertEquals("37,900", responseDto.총액)
        assertEquals("A", responseDto.detailList[0].브랜드)
        assertEquals("11,200", responseDto.detailList[0].가격)
        assertEquals("G", responseDto.detailList[1].브랜드)
        assertEquals("5,500", responseDto.detailList[1].가격)
        assertEquals("B", responseDto.detailList[2].브랜드)
        assertEquals("4,400", responseDto.detailList[2].가격)
        assertEquals("E", responseDto.detailList[3].브랜드)
        assertEquals("9,000", responseDto.detailList[3].가격)
        assertEquals("D", responseDto.detailList[4].브랜드)
        assertEquals("2,000", responseDto.detailList[4].가격)
        assertEquals("I", responseDto.detailList[5].브랜드)
        assertEquals("1,700", responseDto.detailList[5].가격)

    }

    @Test
    fun testGetLowestBrandPriceProduct() {
        // 에러 테스트(상품 등록 전)
        doReturn(emptyList<Product>()).`when`(productRepository).findLowestPriceProductsBySingleBrand()
        val exception1 = assertThrows(CustomException::class.java) {
            productService.getLowestBrandPriceProduct()
        }
        assertEquals("등록된 상품이 없습니다.", exception1.message)

        // mock data 테스트
        val mockResult = listOf(
            Product().apply { id = 25; brandCode = "D"; categoryCode = "TOP"; price = 10100 },
            Product().apply { id = 26; brandCode = "D"; categoryCode = "OUTER"; price = 5100 },
            Product().apply { id = 27; brandCode = "D"; categoryCode = "PANTS"; price = 3000 },
            Product().apply { id = 28; brandCode = "D"; categoryCode = "SNEAKERS"; price = 9500 },
            Product().apply { id = 29; brandCode = "D"; categoryCode = "BAG"; price = 2500 },
            Product().apply { id = 30; brandCode = "D"; categoryCode = "HAT"; price = 1500 },
            Product().apply { id = 31; brandCode = "D"; categoryCode = "SOCKS"; price = 2400 },
            Product().apply { id = 32; brandCode = "D"; categoryCode = "ACCESSORY"; price = 2000 },
        )
        doReturn(mockResult).`when`(productRepository).findLowestPriceProductsBySingleBrand()

        val responseDto = productService.getLowestBrandPriceProduct()
        val detail = responseDto.최저가
        assertNotNull(detail)
        assertEquals("D", detail.브랜드)
        assertEquals("36,100", detail.총액)
        assertNotNull(detail.카테고리)
        val categoryList = detail.카테고리
        assertEquals("상의", categoryList[0].카테고리)
        assertEquals("10,100", categoryList[0].가격)
        assertEquals("아우터", categoryList[1].카테고리)
        assertEquals("5,100", categoryList[1].가격)
        assertEquals("바지", categoryList[2].카테고리)
        assertEquals("3,000", categoryList[2].가격)
        assertEquals("스니커즈", categoryList[3].카테고리)
        assertEquals("9,500", categoryList[3].가격)
        assertEquals("가방", categoryList[4].카테고리)
        assertEquals("2,500", categoryList[4].가격)
        assertEquals("모자", categoryList[5].카테고리)
        assertEquals("1,500", categoryList[5].가격)
        assertEquals("양말", categoryList[6].카테고리)
        assertEquals("2,400", categoryList[6].가격)
        assertEquals("액세서리", categoryList[7].카테고리)
        assertEquals("2,000", categoryList[7].가격)
    }

    @Test
    fun testGetMinMaxPriceProductByCategory() {
        // null 테스트
        doReturn(emptyList<Product>()).`when`(productRepository).findMinPriceProductByCategory(anyString())
        doReturn(emptyList<Product>()).`when`(productRepository).findMaxPriceProductByCategory(anyString())
        val emptyResult = productService.getMinMaxPriceProductByCategory("아우터")
        assertNotNull(emptyResult)
        assertEquals("아우터", emptyResult.카테고리)
        assertEquals(true, emptyResult.최저가.isEmpty())
        assertEquals(true, emptyResult.최고가.isEmpty())

        // 미존재 카테고리 조회(카테고리가 없어도 에러를 발생시키진 않음)
        val emptyResult2 = productService.getMinMaxPriceProductByCategory("속옷")
        assertNotNull(emptyResult2)
        assertEquals("속옷", emptyResult2.카테고리)
        assertEquals(true, emptyResult2.최저가.isEmpty())
        assertEquals(true, emptyResult2.최고가.isEmpty())

        // 에러 테스트(not null 쿼리가 null인 경우)
        doReturn(null).`when`(productRepository).findMinPriceProductByCategory(anyString())
        val exception1 = assertThrows(CustomException::class.java) {
            productService.getMinMaxPriceProductByCategory("상의")
        }
        val regex = Regex("최저, 최고가 목록 조회 실패.*")
        assertTrue(regex.matches(exception1.message.orEmpty()))

        // mock data 테스트
        val mockMinResult = listOf(
            Product().apply { id = 1; brandCode = "C"; categoryCode = "TOP"; price = 10100 },
        )
        val mockMaxResult = listOf(
            Product().apply { id = 2; brandCode = "G"; categoryCode = "TOP"; price = 11400 },
            Product().apply { id = 3; brandCode = "I"; categoryCode = "TOP"; price = 11400 }
        )
        doReturn(mockMinResult).`when`(productRepository).findMinPriceProductByCategory("TOP")
        doReturn(mockMaxResult).`when`(productRepository).findMaxPriceProductByCategory("TOP")

        val responseDto = productService.getMinMaxPriceProductByCategory("상의")
        assertNotNull(responseDto)
        assertEquals("상의", responseDto.카테고리)
        assertEquals(1, responseDto.최저가.size)
        assertEquals(2, responseDto.최고가.size)
        assertEquals("C", responseDto.최저가[0].브랜드)
        assertEquals("10,100", responseDto.최저가[0].가격)
        assertEquals("G", responseDto.최고가[0].브랜드)
        assertEquals("11,400", responseDto.최고가[0].가격)
        assertEquals("I", responseDto.최고가[1].브랜드)
        assertEquals("11,400", responseDto.최고가[1].가격)
    }

    @Test
    fun testCreateProduct() {
        reset(productRepository)
        val createRequest = ProductRequestDto("", "A_상의_2", "A", "OUTER", 18000, "mock_tester")
        // 에러 테스트(insert 결과 없음)
        doReturn(null).`when`(productRepository).save(any(Product::class.java))
        val exception1 = assertThrows(CustomException::class.java) {
            productService.insertProduct(createRequest)
        }
        assertNotNull(exception1)
        assertEquals("상품 생성 쿼리 에러", exception1.message)

        // mocking 리셋
        reset(productRepository)

        val createResponse = productService.insertProduct(createRequest)
        assertNotNull(createResponse)
        assertTrue(createResponse.id > 0)
        assertEquals("A_OUTER_2", createResponse.productNo)
        assertEquals("A_상의_2", createResponse.productName)
        assertEquals("A", createResponse.brandCode)
        assertEquals("OUTER", createResponse.categoryCode)
        assertEquals(18000, createResponse.price)
        assertEquals("INSERT", createResponse.transactionType)
        assertEquals("성공", createResponse.result)
    }

    @Test
    fun testUpdateProduct() {
        reset(productRepository)
        // test 데이터 생성
        val createRequest = ProductRequestDto("", "A_상의_2", "A", "OUTER", 18000, "mock_tester")
        val createResponse = productService.insertProduct(createRequest)
        val createdId = createResponse.id

        // 에러 테스트(update 대상 없음)
        val updateRequest = ProductRequestDto(createResponse.productNo, "A_상의_2_수정", "A", "OUTER", 19000, "mock_tester")
        val exception1 = assertThrows(CustomException::class.java) {
            productService.updateProduct(-100, updateRequest) // 잘못된 상품ID
        }
        assertNotNull(exception1)
        assertEquals("수정 대상 상품 없음", exception1.message)

        // 쿼리 에러 케이스
        doReturn(null).`when`(productRepository).save(any(Product::class.java))
        val exception2 = assertThrows(CustomException::class.java) {
            productService.updateProduct(createdId, updateRequest) // 정상 파라미터
        }
        assertNotNull(exception2)
        assertEquals("상품 수정 쿼리 에러", exception2.message)

        // 쿼리 에러 케이스2
        doReturn(Product().apply { id = -999 }).`when`(productRepository).save(any(Product::class.java))
        val errorResponse = productService.updateProduct(createdId, updateRequest) // 정상 파라미터
        assertNotNull(errorResponse)
        assertEquals("UPDATE", errorResponse.transactionType)
        assertEquals("실패", errorResponse.result)

        // mocking 리셋
        reset(productRepository)

        // 정상 케이스
        val updateResponse = productService.updateProduct(createdId, updateRequest)
        assertNotNull(updateResponse)
        assertTrue(updateResponse.id > 0)
        assertEquals("A_상의_2_수정", updateResponse.productName)
        assertEquals("A", updateResponse.brandCode)
        assertEquals("OUTER", updateResponse.categoryCode)
        assertEquals(19000, updateResponse.price)
        assertEquals("UPDATE", updateResponse.transactionType)
        assertEquals("성공", updateResponse.result)
    }

    @Test
    fun testDeleteProduct() {
        reset(productRepository)
        // 에러 테스트(delete 대상 없음)
        val exception1 = assertThrows(CustomException::class.java) {
            productService.deleteProduct(-100) // 잘못된 상품ID
        }
        assertNotNull(exception1)
        assertEquals("삭제 대상 상품 없음", exception1.message)

        // 쿼리 에러 케이스
        doThrow(RuntimeException("delete failed")).`when`(productRepository).deleteById(anyLong())
        val exception2 = assertThrows(CustomException::class.java) {
            productService.deleteProduct(3) // 정상 파라미터
        }
        verify(productRepository, times(1)).deleteById(3)
        assertNotNull(exception2)
        assertEquals("상품 삭제 쿼리 에러", exception2.message)

        // 정상 케이스
        doNothing().`when`(productRepository).deleteById(anyLong())
        val deleteResponse = productService.deleteProduct(3)
        verify(productRepository, times(2)).deleteById(3)
        assertNotNull(deleteResponse)
        assertEquals("DELETE", deleteResponse.transactionType)
        assertEquals("성공", deleteResponse.result)
    }

    companion object {
        @JvmStatic
        @BeforeAll
        fun setUpProduct(): Unit {
        }
    }
}