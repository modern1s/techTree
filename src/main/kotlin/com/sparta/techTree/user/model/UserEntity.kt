package com.sparta.techTree.user.model

import com.sparta.techTree.common.model.BaseTimeEntity
import com.sparta.techTree.user.dto.UserResponse
import jakarta.persistence.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(name = "uk_member_login_id", columnNames = ["email"])]
)
class UserEntity(
    email: String,
    password: String,
    birth: String,
    name: String,
    nickname: String,
    techStack: String
) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false, length = 30, updatable = false)
    var email = email

    @Column(nullable = false, length = 100)
    var password = password

    @Column(nullable = false, length = 10)
    var name = name

    @Column(nullable = false, length = 10)
    var nickname = nickname

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    var birth = birth

    @Column(nullable = false)
    var techStack = techStack

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    val userRole: List<UserRoleEntity>? = null

    private fun LocalDate.formatDate(): String =
        this.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
}

fun UserEntity.toResponse(): UserResponse {
    return UserResponse(
        id = id!!,
        email = email,
        name = name,
        nickname = nickname,
        birth = birth,
        techStack = techStack,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}