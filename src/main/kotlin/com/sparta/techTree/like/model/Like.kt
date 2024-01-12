package com.sparta.techTree.like.model



import com.sparta.techTree.comment.model.Comment
import com.sparta.techTree.like.dto.CommentLikeResponse
import com.sparta.techTree.like.dto.PostLikeResponse
import com.sparta.techTree.post.model.Post
import com.sparta.techTree.user.model.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "like_table")
class Like(
    @ManyToOne
    @JoinColumn(name = "post_id")
    val post: Post?,

    @ManyToOne
    @JoinColumn(name = "comment_id")
    val comment: Comment?,

    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "liked")
    var liked: Boolean = false,

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    val user: UserEntity

    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun Like.toPostLikeResponse(): PostLikeResponse {
    return PostLikeResponse(
        userId = userId,
        liked = liked,
    )
}

fun Like.toCommentLikeResponse(): CommentLikeResponse {
    return CommentLikeResponse(
        userId = userId,
        liked = liked
    )
}


