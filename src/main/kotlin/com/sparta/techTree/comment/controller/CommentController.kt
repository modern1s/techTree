package com.sparta.techTree.comment.controller

import com.sparta.techTree.comment.dto.CommentDTO
import com.sparta.techTree.comment.dto.CommentResponse
import com.sparta.techTree.comment.dto.UpdateCommentRequest
import com.sparta.techTree.comment.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comments")
class CommentController(private val commentService: CommentService) {

    @GetMapping("/{postId}")
    fun getCommentsByPost(@PathVariable postId: Long): ResponseEntity<List<CommentResponse>> {
        val comments = commentService.getCommentsByPost(postId)
        return ResponseEntity.ok(comments)
    }

    @PostMapping
    fun createComment(@RequestBody commentDto: CommentDTO): ResponseEntity<CommentResponse> {
        val createdComment = commentService.createComment(commentDto)
        return ResponseEntity.ok(createdComment)
    }
    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable commentId: Long,
        @RequestHeader userId: Long,
        @RequestBody updateCommentRequest: UpdateCommentRequest
    ):  ResponseEntity<CommentResponse>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.updateComment(commentId,userId, updateCommentRequest))

    }
    @DeleteMapping("/{commentId}")
    fun deleteComment(@PathVariable commentId: Long,@RequestHeader userId: Long,):ResponseEntity<Unit>
    {
        commentService.deleteComment(commentId, userId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}