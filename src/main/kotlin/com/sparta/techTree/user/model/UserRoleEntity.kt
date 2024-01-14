package com.sparta.techTree.user.model

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
class UserRoleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long?= null,

    @Column(nullable= false, length = 30)
    @Enumerated(EnumType.STRING)
    val role: UserRole,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_user_role_member_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    val user: UserEntity
)