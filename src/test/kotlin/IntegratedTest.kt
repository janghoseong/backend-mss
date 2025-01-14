import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import product.demo.ProductApplication
import product.dto.request.BrandRequestDto
import product.dto.request.ProductRequestDto
import product.dto.response.BrandResponseDto
import product.dto.response.ProductResponseDto

@SpringBootTest(classes = [ProductApplication::class])
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IntegratedTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    /**
     * 구현1 테스트 - 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
     */
    @Test
    fun testCase1() {
        mockMvc.perform(get("/product/lowest-price").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.총액").value("34,100"))
            .andExpect(jsonPath("$.detailList[0].카테고리").value("상의"))
            .andExpect(jsonPath("$.detailList[0].브랜드").value("C"))
            .andExpect(jsonPath("$.detailList[0].가격").value("10,000"))
            .andExpect(jsonPath("$.detailList[1].카테고리").value("아우터"))
            .andExpect(jsonPath("$.detailList[1].브랜드").value("E"))
            .andExpect(jsonPath("$.detailList[1].가격").value("5,000"))
            .andExpect(jsonPath("$.detailList[2].카테고리").value("바지"))
            .andExpect(jsonPath("$.detailList[2].브랜드").value("D"))
            .andExpect(jsonPath("$.detailList[2].가격").value("3,000"))
            .andExpect(jsonPath("$.detailList[3].카테고리").value("스니커즈"))
            .andExpect(jsonPath("$.detailList[3].브랜드").value("G"))
            .andExpect(jsonPath("$.detailList[3].가격").value("9,000"))
            .andExpect(jsonPath("$.detailList[4].카테고리").value("가방"))
            .andExpect(jsonPath("$.detailList[4].브랜드").value("A"))
            .andExpect(jsonPath("$.detailList[4].가격").value("2,000"))
            .andExpect(jsonPath("$.detailList[5].카테고리").value("모자"))
            .andExpect(jsonPath("$.detailList[5].브랜드").value("D"))
            .andExpect(jsonPath("$.detailList[5].가격").value("1,500"))
            .andExpect(jsonPath("$.detailList[6].카테고리").value("양말"))
            .andExpect(jsonPath("$.detailList[6].브랜드").value("I"))
            .andExpect(jsonPath("$.detailList[6].가격").value("1,700"))
            .andExpect(jsonPath("$.detailList[7].카테고리").value("액세서리"))
            .andExpect(jsonPath("$.detailList[7].브랜드").value("F"))
            .andExpect(jsonPath("$.detailList[7].가격").value("1,900"))
    }

    /**
     * 구현2 테스트 - 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을
     * 조회하는 API
     */
    @Test
    fun testCase2() {
        mockMvc.perform(get("/product/lowest-brand-price").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.최저가.브랜드").value("D"))
            .andExpect(jsonPath("$.최저가.카테고리[0].카테고리").value("상의"))
            .andExpect(jsonPath("$.최저가.카테고리[0].가격").value("10,100"))
            .andExpect(jsonPath("$.최저가.카테고리[1].카테고리").value("아우터"))
            .andExpect(jsonPath("$.최저가.카테고리[1].가격").value("5,100"))
            .andExpect(jsonPath("$.최저가.카테고리[2].카테고리").value("바지"))
            .andExpect(jsonPath("$.최저가.카테고리[2].가격").value("3,000"))
            .andExpect(jsonPath("$.최저가.카테고리[3].카테고리").value("스니커즈"))
            .andExpect(jsonPath("$.최저가.카테고리[3].가격").value("9,500"))
            .andExpect(jsonPath("$.최저가.카테고리[4].카테고리").value("가방"))
            .andExpect(jsonPath("$.최저가.카테고리[4].가격").value("2,500"))
            .andExpect(jsonPath("$.최저가.카테고리[5].카테고리").value("모자"))
            .andExpect(jsonPath("$.최저가.카테고리[5].가격").value("1,500"))
            .andExpect(jsonPath("$.최저가.카테고리[6].카테고리").value("양말"))
            .andExpect(jsonPath("$.최저가.카테고리[6].가격").value("2,400"))
            .andExpect(jsonPath("$.최저가.카테고리[7].카테고리").value("액세서리"))
            .andExpect(jsonPath("$.최저가.카테고리[7].가격").value("2,000"))
    }

    /**
     * 구현3 테스트 - 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
     */
    @Test
    fun testCase3() {
        mockMvc.perform(get("/product/min-max-category-price/{categoryName}","상의").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.카테고리").value("상의"))
            .andExpect(jsonPath("$.최저가[0].브랜드").value("C"))
            .andExpect(jsonPath("$.최저가[0].가격").value("10,000"))
            .andExpect(jsonPath("$.최고가[0].브랜드").value("I"))
            .andExpect(jsonPath("$.최고가[0].가격").value("11,400"))
    }

    /**
     * 구현4 테스트 - 브랜드 추가/업데이트/삭제
     */
    @Test
    fun testCase4() {
        val createBrandDto = BrandRequestDto("Z", "Z", "integrated_test")
        val createResult = mockMvc.perform(post("/brand").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(createBrandDto))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.id", Matchers.greaterThan(0)))
            .andExpect(jsonPath("$.brandCode").value("Z"))
            .andExpect(jsonPath("$.brandName").value("Z"))
            .andExpect(jsonPath("$.transactionType").value("INSERT"))
            .andExpect(jsonPath("$.result").value("성공"))
            .andReturn()

        val createResponseContent = createResult.response.contentAsString
        val responseDto1 = objectMapper.readValue(createResponseContent, BrandResponseDto::class.java)
        val brandId = responseDto1.id

        val updateBrandDto = BrandRequestDto("Z2", "Z2", "integrated_test")
        mockMvc.perform(put("/brand/{id}", brandId).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateBrandDto))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.brandCode").value("Z2"))
            .andExpect(jsonPath("$.brandName").value("Z2"))
            .andExpect(jsonPath("$.transactionType").value("UPDATE"))
            .andExpect(jsonPath("$.result").value("성공"))

        mockMvc.perform(delete("/brand/{id}", brandId).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.transactionType").value("DELETE"))
            .andExpect(jsonPath("$.result").value("성공"))
    }

    /**
     * 구현4 테스트 - 상품 추가/업데이트/삭제
     */
    @Test
    fun testCase5() {
        val createProductDto = ProductRequestDto("", "A_상의_2", "A", "OUTER", 18000, "mock_tester")
        val createResult = mockMvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(createProductDto))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.id", Matchers.greaterThan(0)))
            .andExpect(jsonPath("$.productNo").value("A_OUTER_2"))
            .andExpect(jsonPath("$.productName").value("A_상의_2"))
            .andExpect(jsonPath("$.brandCode").value("A"))
            .andExpect(jsonPath("$.categoryCode").value("OUTER"))
            .andExpect(jsonPath("$.price").value(18000))
            .andExpect(jsonPath("$.transactionType").value("INSERT"))
            .andExpect(jsonPath("$.result").value("성공"))
            .andReturn()

        val createResponseContent = createResult.response.contentAsString
        val responseDto1 = objectMapper.readValue(createResponseContent, ProductResponseDto::class.java)
        val productId = responseDto1.id

        val updateProductDto = ProductRequestDto("", "A_상의_2_수정", "A", "OUTER", 19000, "mock_tester")
        mockMvc.perform(put("/product/{id}", productId).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateProductDto))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.productNo").value("A_OUTER_2"))
            .andExpect(jsonPath("$.productName").value("A_상의_2_수정"))
            .andExpect(jsonPath("$.brandCode").value("A"))
            .andExpect(jsonPath("$.categoryCode").value("OUTER"))
            .andExpect(jsonPath("$.price").value(19000))
            .andExpect(jsonPath("$.transactionType").value("UPDATE"))
            .andExpect(jsonPath("$.result").value("성공"))

        mockMvc.perform(delete("/product/{id}", productId).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.transactionType").value("DELETE"))
            .andExpect(jsonPath("$.result").value("성공"))
    }
}