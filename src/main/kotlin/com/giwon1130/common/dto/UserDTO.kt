package com.giwon1130.common.dto

import io.swagger.v3.oas.annotations.media.Schema

/**
 * User 정보를 담는 공통 DTO (BMOA, nK 공통)
 */
data class UserDTO(
    @Schema(description = "사용자의 이메일 주소", example = "user@example.com")
    val email: String,

    @Schema(description = "암호화된 비밀번호")
    val password: String
)
