package com.sparta.techTree.post.model

import com.sparta.techTree.post.dto.PostResponse
import jakarta.persistence.*


@Entity
@Table(name = "post")
class Post(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,

    @Column(name = "title", nullable = false) var title: String,

    @Column(name = "content") var content: String,

    )

fun Post.toResponse(): PostResponse {
    return PostResponse(
        id = id!!,
        title = title,
        content = content,
    )
}