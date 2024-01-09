package com.sparta.techTree.comment.model

import com.sparta.techTree.post.model.BaseTimeEntity
import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "comment")
class Comment(content: String, userId: Long, postId: Long) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "content")
    var content = content

    @Column(name = "user_id")
    val userId = userId

    @Column(name = "post_id")
    val postId = postId
}


