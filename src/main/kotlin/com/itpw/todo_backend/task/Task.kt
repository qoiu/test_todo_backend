package com.itpw.todo_backend.task

import com.itpw.todo_backend.user.User
import jakarta.persistence.*
import java.util.*

@Entity
@Table(
    name = "tasks"
)
class Task (
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY,
    )
    val id: Long = -1L,
    var title: String = "",
    var description: String = "",
    @Temporal(TemporalType.TIMESTAMP)
    var startTime: Calendar = Calendar.getInstance(),
    @ManyToOne
    var owner: User,
    var isFinished: Boolean = false,
    var isDeleted: Boolean = false,
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(name="users_task",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "task_id", referencedColumnName = "id")]
        )
    var observers: MutableList<User> = mutableListOf()
)