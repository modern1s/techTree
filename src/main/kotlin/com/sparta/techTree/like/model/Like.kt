package com.sparta.techTree.like.model



import com.sparta.techTree.like.dto.PostLikeResponse
import jakarta.persistence.*

@Entity
@Table(name = "like_table")
class Like(
    @Column(name = "post_id")
    val postId: Long?,

    @Column(name = "comment_id")
    val commentId: Long?,

    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "liked")
    var liked: Boolean = false
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun Like.toResponse(): PostLikeResponse {
    return PostLikeResponse(
        postId = postId!!,
        userId = userId,
        liked = liked)
}