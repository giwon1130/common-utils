package com.example.common

import com.example.common.auth.JwtUtil
import com.example.common.utils.DateUtils

fun main() {
    println("=== Common Utils Sample Test ===")

    // JWT 생성 및 검증 테스트
    val token = JwtUtil.generateToken("test@example.com")
    println("Generated JWT Token: $token")

    val isValid = JwtUtil.validateToken(token)
    println("Is JWT Token Valid? $isValid")

    // 날짜 유틸리티 테스트
    val formattedDate = DateUtils.nowFormatted()
    println("Formatted Date: $formattedDate")

}
