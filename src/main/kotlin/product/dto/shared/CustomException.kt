package product.dto.shared

class CustomException(
    message: String,
    val failedValue: Any?
): Exception(message)