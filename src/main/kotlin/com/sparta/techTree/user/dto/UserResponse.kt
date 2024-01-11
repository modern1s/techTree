package com.sparta.techTree.user.dto

import java.time.LocalDateTime

data class UserResponse(
    val id: Long,
    val email: String,
    val name: String,
    val nickname: String,
    val birth: String,
    val techStack: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime

)