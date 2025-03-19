package com.giwon1130.common.repository

import com.giwon1130.common.dto.UserDTO
import java.util.Optional

interface UserRepositoryInterface {
    fun findByEmail(email: String): Optional<UserDTO>
}
