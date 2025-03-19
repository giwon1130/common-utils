package com.giwon1130.common.auth

import com.giwon1130.common.dto.LoginRequest
import com.giwon1130.common.dto.RefreshTokenRequest
import com.giwon1130.common.dto.UserDTO
import com.giwon1130.common.repository.UserRepositoryInterface
import com.giwon1130.common.exception.CustomException
import com.giwon1130.common.exception.ErrorCode
import mu.KotlinLogging
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import sia.bmoa.application.dto.LoginResponse

@Service
class AuthService(
    private val userRepository: UserRepositoryInterface,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil,
    private val refreshTokenService: RefreshTokenService
) {
    private val logger = KotlinLogging.logger {}

    fun login(request: LoginRequest): LoginResponse {
        logger.info("로그인 요청 - email: {}", request.email)

        val user: UserDTO = userRepository.findByEmail(request.email).orElseThrow {
            logger.warn("로그인 실패 - 존재하지 않는 email: {}", request.email)
            CustomException(ErrorCode.INVALID_CREDENTIALS)
        }

        if (!passwordEncoder.matches(request.password, user.password)) {
            logger.warn("로그인 실패 - 비밀번호 불일치 - email: {}", request.email)
            throw CustomException(ErrorCode.INVALID_CREDENTIALS)
        }

        val accessToken = jwtUtil.generateToken(user.email)
        val refreshToken = refreshTokenService.createRefreshToken(user.email)

        logger.info("로그인 성공 - email: {}", request.email)
        return LoginResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun refresh(request: RefreshTokenRequest): LoginResponse {
        logger.info("Access Token 갱신 요청 - Refresh Token: {}", request.refreshToken)

        val (newAccessToken, newRefreshToken) = refreshTokenService.generateAccessTokenFromRefreshToken(request.refreshToken)

        logger.info("Access Token 갱신 완료 - 새로운 Access Token 발급됨")
        return LoginResponse(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken
        )
    }
}
