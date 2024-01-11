package com.sparta.techTree.comment.model

import com.sparta.techTree.comment.dto.CommentResponse
import com.sparta.techTree.common.model.BaseTimeEntity
import jakarta.persistence.*


@Entity
@Table(name = "comment")
class Comment(content: String, userId: Long, postId: Long) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var content = content

    @Column(nullable = false)
    val userId = userId

    @Column(nullable = false)
    val postId = postId
}

fun Comment.toResponse(): CommentResponse {
    return CommentResponse(
        id = id!!,
        userId = userId,
        postId = postId,
        content = content,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}
