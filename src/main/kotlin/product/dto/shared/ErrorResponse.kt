package product.dto.shared

import org.springframework.http.HttpStatusCode

/**
 * 에러 리턴용 공통응답
 */
data class ErrorResponse(
    val statusCode: HttpStatusCode,
    val errorMessage: String?,
    val failedValue: Any?,
)
