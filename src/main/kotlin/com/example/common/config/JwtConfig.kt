package com.example.common.config

import com.example.common.auth.JwtUtil
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class JwtConfig(
    @Value("\${jwt.secret}") private val secretKey: String,
    @Value("\${jwt.expiration}") private val expirationMs: Long
) {
    @PostConstruct
    fun init() {
        JwtUtil.initialize(secretKey, expirationMs)
    }
}
