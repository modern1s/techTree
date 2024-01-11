package com.sparta.techTree.post.model

import com.sparta.techTree.post.dto.PostResponse
import jakarta.persistence.*


@Entity
@Table(name = "post")
class Post(title: String, content: String) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var title = title

    @Column(nullable = false)
    var content = content
}

fun Post.toResponse(): PostResponse {
    return PostResponse(
        id = id!!,
        title = title,
        content = content,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}