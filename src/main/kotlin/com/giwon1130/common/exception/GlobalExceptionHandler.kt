package com.giwon1130.common.exception

import com.giwon1130.common.response.ErrorResponse
import jakarta.persistence.EntityNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import org.springframework.data.redis.RedisConnectionFailureException
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    /**
     * 사용자를 찾을 수 없는 경우 (예: `findById()`에서 Optional이 비어있을 때)
     */
    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(e: EntityNotFoundException): ResponseEntity<ErrorResponse> {
        logger.warn("사용자 없음: {}", e.message)
        return createErrorResponse(ErrorCode.ENTITY_NOT_FOUND, e.message)
    }

    /**
     * 잘못된 요청 (예: IllegalArgumentException)
     */
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        logger.warn("잘못된 요청: {}", e.message)
        return createErrorResponse(ErrorCode.INVALID_REQUEST, e.message)
    }

    /**
     * ✅ `CustomException` 처리 추가 (예: 회원가입 시 중복된 이메일, 로그인 실패 등)
     */
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<ErrorResponse> {
        logger.warn("비즈니스 예외 발생 - errorCode: {}, message: {}", e.errorCode.code, e.message)
        return createErrorResponse(e.errorCode, e.message)
    }

    /**
     * 기타 예외 (서버 내부 오류)
     */
    @ExceptionHandler(Exception::class)
    fun handleGeneralException(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error("서버 오류 발생: {}", e.message, e)
        return createErrorResponse(
            ErrorCode.INTERNAL_SERVER_ERROR,
            e.message ?: ErrorCode.INTERNAL_SERVER_ERROR.defaultMessage
        )
    }

    /**
     * Redis 연결 오류 공통 처리
     */
    @ExceptionHandler(RedisConnectionFailureException::class)
    fun handleRedisConnectionException(e: RedisConnectionFailureException): ResponseEntity<ErrorResponse> {
        logger.error("Redis 연결 오류 발생", e)
        return createErrorResponse(ErrorCode.REDIS_CONNECTION_FAILED, e.message)
    }

    /**
     * 기타 데이터베이스 관련 오류 처리 (예: Redis, PostgreSQL)
     */
    @ExceptionHandler(DataAccessException::class)
    fun handleDatabaseException(e: DataAccessException): ResponseEntity<ErrorResponse> {
        logger.error("데이터베이스 오류 발생", e)
        return createErrorResponse(ErrorCode.DATABASE_ERROR, e.message)
    }

    /**
     * dto 유효성 관련 오류 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errorMessages = ex.bindingResult.allErrors
            .map { error ->
                (error as FieldError).defaultMessage
            }
            .joinToString(", ")

        logger.error("유효성 검사 오류 발생: {}", errorMessages)
        return createErrorResponse(ErrorCode.INVALID_REQUEST, errorMessages)
    }

    /**
     * 공통 ErrorResponse 생성
     */
    private fun createErrorResponse(errorCode: ErrorCode, message: String?): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(errorCode.httpStatus).body(
            ErrorResponse(
                errorCode = errorCode.code,
                message = message ?: errorCode.defaultMessage
            )
        )
    }
}