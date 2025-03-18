package com.giwon1130.common.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseUserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long = 0,

    @Column(nullable = false, unique = true)
    open val email: String,

    @Column(nullable = false)
    open var password: String,

    @Column(nullable = false)
    open val name: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    open val role: UserRole = UserRole.USER,

    @Column(nullable = false)
    open val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    open var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    open var isDeleted: Boolean = false, // 삭제 여부 (논리 삭제)

    @Column(nullable = true)
    open var deletedAt: LocalDateTime? = null // 삭제 처리 시간
) {

    @PreUpdate
    fun preUpdate() {
        updatedAt = LocalDateTime.now()
    }

    fun delete() {
        isDeleted = true
        deletedAt = LocalDateTime.now()
    }

    fun restore() {
        isDeleted = false
        deletedAt = null
    }
}

enum class UserRole {
    USER, ADMIN
}
