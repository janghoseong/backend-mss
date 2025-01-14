package product.dto.request

import java.sql.Timestamp

/**
 * 상품 CUD 요청 DTO
 */
data class ProductRequestDto(
    val productNo: String,
    val productName: String,
    val brandCode: String,
    val categoryCode: String,
    val price: Long,
    val requestedBy: String,
)
