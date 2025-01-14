package product.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import product.dto.request.ProductRequestDto
import product.service.ProductService

@RestController
@RequestMapping("/product")
class ProductController(
    private val productService: ProductService,
) {

    /**
     * 구현 1) - 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
     */
    @GetMapping("/lowest-price")
    fun getLowestPriceProducts(
            request: HttpServletRequest,
            response: HttpServletResponse,
            @RequestParam param: HashMap<String, Any>): ResponseEntity<Any>
    {
        val responseDto = productService.getLowestPriceProducts()
        return ResponseEntity.ok(responseDto)
    }

    /**
     * 구현 2) - 단일 브랜드 최저가 상품 가격, 총액을 조회하는 API
     */
    @GetMapping("/lowest-brand-price")
    fun getLowestBrandPriceProducts(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @RequestParam param: HashMap<String, Any>): ResponseEntity<Any>
    {
        val responseDto = productService.getLowestBrandPriceProduct()
        return ResponseEntity.ok(responseDto)
    }

    /**
     * 구현 3) - 카테고리 이름으로 최저, 최고 가격 브랜드와 상품가격을 조회하는 API
     */
    @GetMapping("/min-max-category-price/{categoryName}")
    fun getMinMaxPriceProductsByCategory(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @PathVariable categoryName: String): ResponseEntity<Any>
    {
        val responseDto = productService.getMinMaxPriceProductByCategory(categoryName)
        return ResponseEntity.ok(responseDto)
    }

    /**
     * 구현 4) - 상품 추가
     */
    @PostMapping
    fun createProduct(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @RequestBody productRequestDto: ProductRequestDto): ResponseEntity<Any>
    {
        val responseDto = productService.insertProduct(productRequestDto)
        return ResponseEntity.ok(responseDto)
    }

    /**
     * 구현 4) - 상품 수정
     */
    @PutMapping("/{id}")
    fun updateProduct(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @PathVariable id: Long,
        @RequestBody productRequestDto: ProductRequestDto): ResponseEntity<Any>
    {
        val responseDto = productService.updateProduct(id, productRequestDto)
        return ResponseEntity.ok(responseDto)
    }

    /**
     * 구현 4) - 상품 삭제
     */
    @DeleteMapping("/{id}")
    fun deleteProduct(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @PathVariable id: Long): ResponseEntity<Any>
    {
        val responseDto = productService.deleteProduct(id)
        return ResponseEntity.ok(responseDto)
    }
}