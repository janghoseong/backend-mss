package product.dto.response

/**
 * 상품 CUD 응답 DTO
 */
data class ProductResponseDto(
    val id: Long,
    val productNo: String,
    val productName: String?,
    val brandCode: String,
    val categoryCode: String,
    val price: Long,
    val transactionType: String,
    val result: String
)