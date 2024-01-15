package com.sparta.techTree.comment.controller

import com.sparta.techTree.comment.dto.CreateCommentRequest
import com.sparta.techTree.comment.dto.CommentResponse
import com.sparta.techTree.comment.dto.UpdateCommentRequest
import com.sparta.techTree.comment.service.CommentService
import com.sparta.techTree.common.dto.CustomUser
import com.sparta.techTree.like.dto.CommentLikeResponse
import com.sparta.techTree.like.service.LikeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comments")
class CommentController(private val commentService: CommentService, private val likeService: LikeService) {

    @GetMapping("/{postId}")
    fun getCommentsByPost(@PathVariable postId: Long): ResponseEntity<List<CommentResponse>> {
        val comments = commentService.getCommentsByPost(postId)
        return ResponseEntity.ok(comments)
    }

    @PostMapping("/{postId}")
    fun createComment(
        @AuthenticationPrincipal user: CustomUser,
        @PathVariable postId: Long, @RequestBody createCommentRequest: CreateCommentRequest
    ): ResponseEntity<CommentResponse> {
        val userId = user.id
        val createdComment = commentService.createComment(postId, userId, createCommentRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment)
    }

    @PatchMapping("/{commentId}")
    fun updateComment(
        @AuthenticationPrincipal user: CustomUser,
        @PathVariable commentId: Long,
        @RequestBody updateCommentRequest: UpdateCommentRequest
    ): ResponseEntity<CommentResponse> {
        val userId = user.id
        return ResponseEntity.status(HttpStatus.OK)
            .body(commentService.updateComment(commentId, userId, updateCommentRequest))
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @AuthenticationPrincipal user: CustomUser,
        @PathVariable commentId: Long
    ): ResponseEntity<Unit> {
        val userId = user.id
        commentService.deleteComment(commentId, userId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @PostMapping("/likes/{commentId}")
    fun createLikeForComment(
        @AuthenticationPrincipal user: CustomUser,
        @PathVariable commentId: Long
    ): ResponseEntity<CommentLikeResponse> {
        val userId = user.id
        return ResponseEntity.status(HttpStatus.CREATED).body(likeService.createLikeForComment(commentId, userId))
    }

    @DeleteMapping("/likes/{commentId}")
    fun deleteLikeForComment(
        @AuthenticationPrincipal user: CustomUser,
        @PathVariable commentId: Long
    ): ResponseEntity<Unit> {
        val userId = user.id
        likeService.deleteLikeForComment(commentId, userId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}