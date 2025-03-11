package com.giwon1130.common.exception

import com.giwon1130.common.response.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        logger.warn("잘못된 요청: {}", e.message)
        return createErrorResponse(ErrorCode.INVALID_REQUEST, e.message)
    }

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<ErrorResponse> {
        logger.warn("비즈니스 예외 발생 - errorCode: {}, message: {}", e.errorCode.code, e.message)
        return createErrorResponse(e.errorCode, e.message)
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error("서버 오류 발생: {}", e.message, e)
        return createErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, e.message ?: ErrorCode.INTERNAL_SERVER_ERROR.defaultMessage)
    }

    private fun createErrorResponse(errorCode: ErrorCode, message: String?): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(errorCode.httpStatus).body(
            ErrorResponse.from(errorCode, message)
        )
    }
}
