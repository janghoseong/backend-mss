package product.dto.response

/**
 * 브랜드 CUD 응답 DTO
 */
data class BrandResponseDto(
    val id: Long,
    val brandCode: String,
    val brandName: String?,
    val transactionType: String,
    val result: String
)