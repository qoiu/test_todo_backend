package com.itpw.todo_backend.authorization

import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(
    name = "auth_users"
)
data class AuthenticationUser (
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY,
    )
    val id: Long = -1L,
    @Column(unique=true)
    val name: String="",
    val password: String="",

): Serializable{


    constructor(name: String, password: String): this(
        id=-1,
        name=name,
        password = password
    )
}