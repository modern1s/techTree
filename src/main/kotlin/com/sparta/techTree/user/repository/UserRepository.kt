package com.sparta.techTree.user.repository

import com.sparta.techTree.user.model.UserEntity
import com.sparta.techTree.user.model.UserRoleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
}

interface UserRoleRepository : JpaRepository<UserRoleEntity, Long> {
}