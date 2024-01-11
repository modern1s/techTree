package com.sparta.techTree.comment.model

import com.sparta.techTree.comment.dto.CommentResponse
import com.sparta.techTree.post.model.BaseTimeEntity
import com.sparta.techTree.post.model.Post
import jakarta.persistence.*

@Entity
@Table(name = "comment")
class Comment(
    @Column(name = "content") var content: String,

    @Column(name = "user_id") val userId: Long,

    @Column(name = "post_id") var postId: Long,

    @Column(name = "count_likes") var countLikes: Long = 0,

    @ManyToOne @JoinColumn(name = "post_connect") val post: Post
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
fun Comment.toResponse(): CommentResponse {
    return CommentResponse(
        id = id!!,
        userId = userId,
        postId = postId,
        content = content,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        countLikes = countLikes
    )
}

