package com.sparta.techTree.post.model

import com.sparta.techTree.comment.model.Comment
import com.sparta.techTree.common.model.BaseTimeEntity
import com.sparta.techTree.like.model.Like
import com.sparta.techTree.like.model.toPostLikeResponse
import com.sparta.techTree.post.dto.PostResponse
import jakarta.persistence.*


@Entity
@Table(name = "post")
class Post(
    @Column(name = "title") var title: String,

    @Column(name = "content") var content: String,

    @Column(name = "user_id") val userId: Long?,

    @Column(name = "count_likes") var countLikes: Long = 0,

    @OneToMany(mappedBy = "post", cascade = [CascadeType.REMOVE])
    val comment: List<Comment> = mutableListOf(),

    @OneToMany(mappedBy = "post", cascade = [CascadeType.REMOVE])
    val likes: List<Like> = mutableListOf()

) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun Post.toResponse(): PostResponse {
    return PostResponse(
        id = id!!,
        title = title,
        content = content,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        userId = userId,
        countLikes = likes.size
    )
}