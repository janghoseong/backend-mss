package product.dto.request

import java.sql.Timestamp

/**
 * 브랜드 CUD 요청 DTO
 */
data class BrandRequestDto(
    val brandCode: String,
    val brandName: String,
    val requestedBy: String,
)
