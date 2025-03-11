package com.example.common.response

import com.example.common.exception.ErrorCode

data class ErrorResponse(
    val status: String = "FAIL",
    val message: String?,
    val errorCode: String
) {
    companion object {
        fun from(errorCode: ErrorCode, message: String? = null): ErrorResponse {
            return ErrorResponse(
                status = "FAIL",
                message = message ?: errorCode.defaultMessage,
                errorCode = errorCode.code
            )
        }
    }
}
