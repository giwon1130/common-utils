package com.example.common.auth

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*
import javax.crypto.spec.SecretKeySpec

/**
 * JWT 토큰 생성 및 검증을 처리하는 유틸리티 클래스.
 */
object JwtUtil {
    private lateinit var secretKey: String
    private var expirationMs: Long = 3600000L

    fun initialize(secret: String, expiration: Long) {
        secretKey = secret
        expirationMs = expiration
    }

    /**
     * 주어진 이메일 정보를 사용하여 JWT 토큰을 생성한다.
     */
    fun generateToken(email: String): String {
        val now = Date()
        val expiryDate = Date(now.time + expirationMs)

        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    /**
     * 토큰에서 이메일(사용자 식별 정보)를 추출한다.
     */
    fun extractEmail(token: String): String? {
        return getClaimsFromToken(token)?.subject
    }

    /**
     * 주어진 토큰이 유효한지 검증한다.
     */
    fun validateToken(token: String): Boolean {
        return try {
            val claims = getClaimsFromToken(token)
            claims != null && claims.expiration.after(Date())
        } catch (e: Exception) {
            false
        }
    }

    /**
     * JWT 서명 키 생성
     */
    private fun getSigningKey(): SecretKeySpec {
        val keyBytes = Base64.getDecoder().decode(secretKey)
        return SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.jcaName)
    }

    /**
     * 토큰에서 Claims(정보)를 추출한다.
     */
    private fun getClaimsFromToken(token: String): Claims? {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: Exception) {
            null
        }
    }
}
