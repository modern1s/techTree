package com.sparta.techTree.comment.controller

import com.sparta.techTree.comment.dto.CreateCommentRequest
import com.sparta.techTree.comment.dto.CommentResponse
import com.sparta.techTree.comment.dto.UpdateCommentRequest
import com.sparta.techTree.comment.model.Comment
import com.sparta.techTree.comment.service.CommentService
import com.sparta.techTree.like.dto.CommentLikeResponse
import com.sparta.techTree.like.dto.PostLikeResponse
import com.sparta.techTree.like.service.LikeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
        @PathVariable postId: Long, @RequestBody createCommentRequest: CreateCommentRequest
    ): ResponseEntity<CommentResponse> {
        val createdComment: CommentResponse = commentService.createComment(postId, createCommentRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment)
    }

    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable commentId: Long,
        @RequestHeader userId: Long,
        @RequestBody updateCommentRequest: UpdateCommentRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(commentService.updateComment(commentId, userId, updateCommentRequest))

    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(@PathVariable commentId: Long, @RequestHeader userId: Long): ResponseEntity<Unit> {
        commentService.deleteComment(commentId, userId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @PostMapping("/likes/{commentId}/{userId}")
    fun createLikeForComment(
        @PathVariable commentId: Long, @PathVariable userId: Long
    ): ResponseEntity<CommentLikeResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(likeService.createLikeForComment(commentId, userId))
    }

    @DeleteMapping("/likes/{commentId}/{userId}")
    fun deleteLikeForComment(@PathVariable commentId: Long, @PathVariable userId: Long): ResponseEntity<Unit> {
        likeService.deleteLikeForComment(commentId, userId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}