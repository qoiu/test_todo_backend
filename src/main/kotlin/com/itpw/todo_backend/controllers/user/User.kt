package com.itpw.todo_backend.controllers.user

import jakarta.persistence.*
import org.hibernate.annotations.Formula
import java.util.*

@Entity
@Table(
    name = "users"
)
class User (
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY,
    )
    val id: Long = -1L,
    var login: String? = null,
    var password: String? = null,
)