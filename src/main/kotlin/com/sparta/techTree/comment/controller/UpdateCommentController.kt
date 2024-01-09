package com.sparta.techTree.comment.controller

import com.sparta.techTree.comment.dto.CommentDTO
import com.sparta.techTree.comment.dto.UpdateCommentRequest
import com.sparta.techTree.comment.service.CommentService
import io.swagger.v3.oas.annotations.headers.Header
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
@RequestMapping("/comments")
@RestController
class UpdateCommentController(
    private val commentService: CommentService
) {

    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable commentId: Long,
        @RequestHeader userId: Long,
        @RequestBody updateCommentRequest: UpdateCommentRequest
    ):  ResponseEntity<CommentDTO>{
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