package com.itpw.todo_backend.authorization

import jakarta.persistence.*

@Entity
@Table(
    name = "users"
)
class AuthenticationUser (
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY,
    )
    val id: Long = -1L,
    val name: String,

)