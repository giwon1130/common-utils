package com.giwon1130.common.auth

import com.giwon1130.common.exception.CustomException
import com.giwon1130.common.exception.ErrorCode
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.*

@Service
class RefreshTokenService(
    private val redisTemplate: RedisTemplate<String, String>,
    private val jwtUtil: JwtUtil,
    @Value("\${redis.prefix}") private val redisPrefix: String // ✅ Redis Key Prefix 적용
) {
    private val logger = KotlinLogging.logger {}
    private val refreshTokenTTL = Duration.ofDays(14) // Refresh Token 유효기간 (14일)

    /**
     * Refresh Token 검증 (Redis에서 조회)
     */
    fun verifyRefreshToken(refreshToken: String): String {
        val redisKey = "$redisPrefix$refreshToken"
        logger.info("Refresh Token 검증 요청 - redisKey: {}", redisKey)

        return redisTemplate.opsForValue().get(redisKey)
            ?: run {
                logger.warn("Refresh Token 검증 실패 - redisKey: {}", redisKey)
                throw CustomException(ErrorCode.INVALID_REFRESH_TOKEN)
            }
    }

    /**
     * Refresh Token을 이용하여 새로운 Access Token 발급
     */
    fun generateAccessTokenFromRefreshToken(refreshToken: String): Pair<String, String> {
        logger.info("Access Token 갱신 요청 - Refresh Token: {}", refreshToken)

        val userEmail = verifyRefreshToken(refreshToken) // Refresh Token 검증

        val newAccessToken = jwtUtil.generateToken(userEmail)
        val newRefreshToken = createRefreshToken(userEmail) // 새로운 Refresh Token 생성

        logger.info("새로운 Access Token 및 Refresh Token 생성 완료 - email: {}", userEmail)

        deleteRefreshToken(refreshToken) // 기존 Refresh Token 삭제 (RTR 적용)
        logger.info("기존 Refresh Token 삭제 완료 - redisKey: {}", "$redisPrefix$refreshToken")

        return Pair(newAccessToken, newRefreshToken)
    }

    /**
     * 새로운 Refresh Token 생성 및 Redis 저장
     */
    fun createRefreshToken(userEmail: String): String {
        val refreshToken = UUID.randomUUID().toString()
        val redisKey = "$redisPrefix$refreshToken"

        logger.info("새로운 Refresh Token 생성 - email: {}, redisKey: {}", userEmail, redisKey)

        redisTemplate.opsForValue().set(redisKey, userEmail, refreshTokenTTL)
        logger.info("Refresh Token 저장 완료 - redisKey: {}, TTL: {} days", redisKey, refreshTokenTTL.toDays())

        return refreshToken
    }

    /**
     * Refresh Token 삭제 (RTR 적용 또는 로그아웃 시)
     */
    fun deleteRefreshToken(refreshToken: String) {
        val redisKey = "$redisPrefix$refreshToken"
        logger.info("Refresh Token 삭제 요청 - redisKey: {}", redisKey)

        redisTemplate.delete(redisKey)
        logger.info("Refresh Token 삭제 완료 - redisKey: {}", redisKey)
    }
}
