package com.sparta.techTree.comment.model

import com.sparta.techTree.post.model.BaseTimeEntity
import com.sparta.techTree.post.model.Post
import jakarta.persistence.*


@Entity
@Table(name = "comment")
class Comment(
    @Column(name = "content") var content: String,

    @Column(name = "user_id") val userId: Long,
    //post와 연결
    @ManyToOne @JoinColumn(name = "post_id") val post: Post
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}


