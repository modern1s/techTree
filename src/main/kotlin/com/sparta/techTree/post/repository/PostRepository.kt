package com.sparta.techTree.post.repository


import com.sparta.techTree.post.model.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long> {

}