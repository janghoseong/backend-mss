package product.handler

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import product.dto.shared.CustomException
import product.dto.shared.ErrorResponse

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(CustomException::class)
    fun handleCommonException(e: CustomException): ResponseEntity<ErrorResponse> {
        e.printStackTrace()
        val errorResponse = ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            e.message,
            e.failedValue
        )
        return ResponseEntity.status(errorResponse.statusCode).body(errorResponse)
    }
}