package com.sparta.techTree.post.model

import com.sparta.techTree.comment.model.Comment
import com.sparta.techTree.common.model.BaseTimeEntity
import com.sparta.techTree.like.model.Like
import com.sparta.techTree.post.dto.PostResponse
import com.sparta.techTree.user.model.UserEntity
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction


@Entity
@Table(name = "post")
class Post(
    @Column(name = "title") var title: String,

    @Column(name = "content") var content: String,

    @Column(name = "count_likes") var countLikes: Long = 0,

    @OneToMany(mappedBy = "post", cascade = [CascadeType.REMOVE])
    val comment: List<Comment> = mutableListOf(),

    @OneToMany(mappedBy = "post", cascade = [CascadeType.REMOVE])
    val likes: List<Like> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val user: UserEntity
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
        userId = user.id!!,
        countLikes = likes.size
    )
}