import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.test.context.ActiveProfiles
import product.demo.ProductApplication
import product.dto.request.BrandRequestDto
import product.dto.shared.CustomException
import product.jpa.entity.Brand
import product.jpa.repo.BrandRepository
import product.service.BrandService
import java.sql.Timestamp

@SpringBootTest(classes = [ProductApplication::class])
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BrandServiceTest(
    @Autowired val brandService: BrandService,
) {

    @SpyBean
    private lateinit var brandRepository: BrandRepository

    @BeforeEach
    fun setUp() {
    }

    @Test
    fun testInsertBrand() {
        reset(brandRepository)
        val createRequest = BrandRequestDto("X", "X", "mock_test")
        val brand = Brand().apply {
            id = 1L
            brandCode = "X"
            brandName = "X"
            createdBy = "mock_test"
            createdAt = Timestamp(System.currentTimeMillis())
        }
        doReturn(brand).`when`(brandRepository).save(any(Brand::class.java))

        val createResponse = brandService.insertBrand(createRequest)

        assertNotNull(createResponse)
        assertEquals(1L, createResponse.id)
        assertEquals("X", createResponse.brandCode)
        assertEquals("X", createResponse.brandName)
        assertEquals("INSERT", createResponse.transactionType)
        assertEquals("성공", createResponse.result)
    }

    @Test
    fun testUpdateBrand() {
        reset(brandRepository)
        val nullResult = assertThrows(CustomException::class.java) {
            brandService.updateBrand(999, BrandRequestDto("Z", "UPDATED_B_TO_Z", "mock_test"))
        }
        assertEquals("수정 대상 브랜드 없음", nullResult.message)

        val updateResponse = brandService.updateBrand(2, BrandRequestDto("Z", "UPDATED_B_TO_Z", "mock_test"))

        assertNotNull(updateResponse)
        assertEquals(2L, updateResponse.id)
        assertEquals("Z", updateResponse.brandCode)
        assertEquals("UPDATED_B_TO_Z", updateResponse.brandName)
        assertEquals("UPDATE", updateResponse.transactionType)
        assertEquals("성공", updateResponse.result)
    }

    @Test
    fun testDeleteBrand() {
        reset(brandRepository)
        val nullResult = assertThrows(CustomException::class.java) {
            brandService.deleteBrand(999)
        }
        assertEquals("삭제 대상 브랜드가 없습니다.", nullResult.message)

        doNothing().`when`(brandRepository).deleteById(anyLong()) // 전체 테스트 꼬이는걸 막기 위해 실처리는 안 함
        val deleteResponse = brandService.deleteBrand(2)

        assertNotNull(deleteResponse)
        assertEquals(2L, deleteResponse.id)
        assertEquals("", deleteResponse.brandCode)
        assertEquals("", deleteResponse.brandName)
        assertEquals("DELETE", deleteResponse.transactionType)
        assertEquals("성공", deleteResponse.result)
    }

    companion object {

        @JvmStatic
        @BeforeAll
        fun setUpBrand(): Unit {
        }
    }
}