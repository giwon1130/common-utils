package com.giwon1130.common.utils

import jakarta.validation.ConstraintViolation
import jakarta.validation.Validator
import org.springframework.stereotype.Component

@Component
class ValidationUtils(private val validator: Validator) {

    fun <T> validate(dto: T) {
        val violations: Set<ConstraintViolation<T>> = validator.validate(dto)
        if (violations.isNotEmpty()) {
            val errorMessages = violations.joinToString(", ") { "${it.propertyPath}: ${it.message}" }
            throw IllegalArgumentException("유효성 검사 실패: $errorMessages")
        }
    }
}
