package com.sparta.techTree.comment.model

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "comment")
class Comment(

    @Column(name = "content")
    var content: String,

    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "post_id")
    val postId: Long,

    @Column(name = "create_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column (name = "update_at")
    var updatedAt: LocalDateTime? = null,

){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}


