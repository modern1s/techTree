package com.sparta.techTree.user.model

import jakarta.persistence.*

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
    val user: UserEntity
)