package com.sparta.techTree.comment.model

import com.sparta.techTree.comment.dto.CommentResponse
import com.sparta.techTree.common.model.BaseTimeEntity
import com.sparta.techTree.like.model.Like
import com.sparta.techTree.post.model.Post
import jakarta.persistence.*

@Entity
@Table(name = "comment")
class Comment(
    @Column(name = "content") var content: String,

    @Column(name = "user_id") val userId: Long,

    @Column(name = "count_likes") var countLikes: Long = 0,

    @ManyToOne @JoinColumn(name = "post_id") val post: Post?,

    @OneToMany(mappedBy = "comment", cascade = [CascadeType.REMOVE])
    val likes: List<Like> = mutableListOf()

    ) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
fun Comment.toResponse(): CommentResponse {
    return CommentResponse(
        id = id!!,
        userId = userId,
        postId = post?.id!!,
        content = content,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        countLikes = likes.size
    )
}


