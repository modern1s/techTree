package com.sparta.techTree.comment.repository

import com.sparta.techTree.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long,> {
    fun findByPostId(postId: Long,): List<Comment>

    fun findByIdAndUserId(id: Long, userId: Long,): Comment?
}