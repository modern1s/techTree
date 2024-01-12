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

    @Column(name = "liked")
    var liked: Boolean = false,

    //연관 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: UserEntity

    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
//연관 관계 설정에 따른 userId 부분 수정
fun Like.toPostLikeResponse(): PostLikeResponse {
    return PostLikeResponse(
        userId = user.id!!,
        liked = liked,
    )
}
//연관 관계 설정에 따른 userId 부분 수정
fun Like.toCommentLikeResponse(): CommentLikeResponse {
    return CommentLikeResponse(
        userId = user.id!!,
        liked = liked
    )
}


