package com.sparta.techTree.comment.dto

//통일성(클린코딩)을 위해 클래스명 변경 및 postid는 가져오기 때문에 여기서 삭제
data class CreateCommentRequest (
    val userId: Long,
    val content: String
)