package com.sparta.techTree.common.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class CustomUser(
    val email: String,
    nickName: String,
    password: String,
    authorities: Collection<GrantedAuthority>
) : User(nickName, password, authorities)
