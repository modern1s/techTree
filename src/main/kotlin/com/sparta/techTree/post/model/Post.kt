package com.sparta.techTree.post.model

import com.sparta.techTree.comment.model.Comment
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
    //comment와 연결 추가
    @OneToMany(mappedBy = "post", cascade = [CascadeType.REMOVE])
    val comment: List<Comment> = mutableListOf()

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