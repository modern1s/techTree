package com.sparta.techTree.user.model

import com.sparta.techTree.comment.model.Comment
import com.sparta.techTree.common.model.BaseTimeEntity
import com.sparta.techTree.like.model.Like
import com.sparta.techTree.post.model.Post
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
): BaseTimeEntity(){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null

    @Column(nullable = false, length = 30, updatable = false)
    var email = email

    @Column(nullable = false, length = 100)
    var password = password

    @Column(nullable = false, length = 10)
    var name = name

    @Column(nullable = false, length = 10)
    var nickname= nickname

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    var birth= birth

    @Column(nullable = false)
    var techStack = techStack

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    val userRole: List<UserRoleEntity>? = null

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
//    val posts: List<Post>? = null
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
//    val comments: List<Comment>? = null
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
//    val likes: List<Like>? = null
//   TODO:필요한지 아닌지 회의(물어보기) 일단은 사용이 안되고 있음.
//   TODO:이것들이 사용되려면 마이페이지 같은걸로 글/댓글/좋아요를 가져오게 한다면 필요할지도?
    private fun LocalDate.formatDate(): String =
        this.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
}

fun UserEntity.toResponse(): UserResponse{
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