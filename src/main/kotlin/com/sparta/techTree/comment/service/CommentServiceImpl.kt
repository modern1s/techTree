package com.sparta.techTree.comment.service

import com.sparta.techTree.comment.dto.CreateCommentRequest
import com.sparta.techTree.comment.dto.CommentResponse
import com.sparta.techTree.comment.dto.UpdateCommentRequest
import com.sparta.techTree.comment.model.Comment
import com.sparta.techTree.comment.repository.CommentRepository
import com.sparta.techTree.exception.ModelNotFoundException
import com.sparta.techTree.post.model.Post
import com.sparta.techTree.post.repository.PostRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository, private val postRepository: PostRepository
) : CommentService {

    override fun updateComment(commentId: Long, userId: Long, request: UpdateCommentRequest): CommentResponse {
        val commentToUpdate = commentRepository.findByIdAndUserId(commentId, userId)
            ?: throw IllegalArgumentException("Comment not found or user not authorized to update")
        commentToUpdate.content = request.content

        commentRepository.save(commentToUpdate)
        //댓글에 맞는 postid를 가져오게 변경
        return CommentResponse(
            id = commentToUpdate.id!!,
            userId = commentToUpdate.userId,
            postId = commentToUpdate.post.id!!,
            content = commentToUpdate.content,
            createdAt = commentToUpdate.createdAt,
            updatedAt = commentToUpdate.updatedAt
        )
    }


    override fun deleteComment(commentId: Long, userId: Long) {
        val deleteComment = commentRepository.findByIdAndUserId(commentId, userId)
            ?: throw IllegalArgumentException("Comment not found or user not authorized to delete")
        commentRepository.delete(deleteComment)
    }

    override fun getCommentsByPost(postId: Long): List<CommentResponse> {
        val comments = commentRepository.findByPostId(postId)
        return comments.map {
            //댓글에 맞는 postid를 가져오게 변경 -> 기대되는 효과는 postid에 따른 무슨 댓글이 달렷는지 확인이 가능해서 원하는 commentid를 추적해서 쉽게 댓글삭제 가능
            CommentResponse(
                it.id!!,
                it.userId,
                it.post.id!!,
                it.content,
                it.createdAt,
                it.updatedAt
            )
        }
    }

    //댓글 생성 로직 변경 -> 기존 내가 원하는 post에 댓글을 생성할수 없었는데 postid를 가져와서 하게함
    //예시)1번 post에 댓글을 달고 싶으면 swagger기준 테스트칸에 1을 입력해야 1번 post에 댓글 생성 가능
    //1번 post가 없는경우 ModelNotFoundException
    @Transactional
    override fun createComment(postId: Long, request: CreateCommentRequest): Comment {
        val post: Post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val comment = Comment(
            post = post, userId = request.userId, content = request.content
        )

        return commentRepository.save(comment)

    }
}

