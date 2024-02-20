package com.itpw.todo_backend.controllers.user

import com.fasterxml.jackson.annotation.JsonProperty
import com.itpw.todo_backend.controllers.task.Task
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
    var name: String? = null,
    var surname: String? = null,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "owner")
    val tasks: List<Task> = emptyList()
)
