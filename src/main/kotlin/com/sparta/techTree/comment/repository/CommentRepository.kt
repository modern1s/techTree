package com.sparta.techTree.comment.repository

import com.sparta.techTree.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Int> {
    fun findByPostId(postId: Int): List<Comment>
}