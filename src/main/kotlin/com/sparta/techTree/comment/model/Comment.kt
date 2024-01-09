package com.sparta.techTree.comment.model

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "comments")
data class Comment (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "userId")
    val userId: Long,

    @Column(name = "postId")
    val postId: Long,

    @Column(name = "content")
    val content: String,

    @Column(name = "createdAt")
    val createdAt: LocalDateTime? = null,

    @Column(name = "updatedAt")
    var updatedAt: LocalDateTime? = null,

    @Column(name = "deletedAt")
    var deletedAt: LocalDateTime? = null
)