package com.sparta.techTree.comment.model

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "comments")
data class Comment (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(name = "userId")
    val userId: Int,

    @Column(name = "postId")
    val postId: Int,

    @Column(name = "content")
    val content: String,

    @Column(name = "createdAt")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updatedAt")
    var updatedAt: LocalDateTime? = null,

    @Column(name = "deletedAt")
    var deletedAt: LocalDateTime? = null
)