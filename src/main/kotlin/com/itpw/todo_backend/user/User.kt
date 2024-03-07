package com.itpw.todo_backend.user

import com.itpw.todo_backend.task.Task
import jakarta.persistence.*

@Entity
@Table(
    name = "users",
    uniqueConstraints = [UniqueConstraint(columnNames = ["login"])]
)
class User (
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY,
    )
    val id: Long = -1L,
    var login: String,
    var password: String,
    var name: String? = null,
    var surname: String? = null,
    @ManyToMany(mappedBy = "observers")
    val tasks: MutableList<Task> = mutableListOf(),
)
