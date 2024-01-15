package com.sparta.techTree.like.repository

import com.sparta.techTree.like.model.Like
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LikeRepository : JpaRepository<Like, Long> {
    fun findByPostIdAndUserId(postId: Long, userId: Long): Like?

    fun countByPostId(postId: Long): Long

    fun findByCommentIdAndUserId(commentId: Long, userId: Long): Like?

    fun countByCommentId(commentId: Long): Long
}