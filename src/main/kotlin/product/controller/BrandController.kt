package product.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import product.dto.request.BrandRequestDto
import product.dto.response.LowestPriceResponseDto
import product.service.BrandService
import product.service.ProductService

@RestController
@RequestMapping("/brand")
class BrandController(
    private val brandService: BrandService
) {

    /**
     * 구현 4) - 브랜드 추가
     */
    @PostMapping
    fun createBrand(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @RequestBody brandRequestDto: BrandRequestDto): ResponseEntity<Any>
    {
        val responseDto = brandService.insertBrand(brandRequestDto)
        return ResponseEntity.ok(responseDto)
    }

    /**
     * 구현 4) - 브랜드 수정
     */
    @PutMapping("/{id}")
    fun updateBrand(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @PathVariable id: Long,
        @RequestBody brandRequestDto: BrandRequestDto
    ): ResponseEntity<Any>
    {
        val responseDto = brandService.updateBrand(id, brandRequestDto)
        return ResponseEntity.ok(responseDto)
    }

    /**
     * 구현 4) - 브랜드 삭제
     */
    @DeleteMapping("/{id}")
    fun deleteBrand(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @PathVariable id: Long): ResponseEntity<Any>
    {
        val responseDto = brandService.deleteBrand(id)
        return ResponseEntity.ok(responseDto)
    }
}