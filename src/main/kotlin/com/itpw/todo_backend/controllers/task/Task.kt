package com.itpw.todo_backend.controllers.task

import com.fasterxml.jackson.annotation.JsonProperty
import com.itpw.todo_backend.controllers.user.User
import jakarta.persistence.*
import org.hibernate.annotations.Formula
import java.time.LocalDate
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
    var owner: User=User(),
    var isFinished: Boolean = false,
    var isDeleted: Boolean = false,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "id")
    var observers: List<User> = emptyList()
){
    fun update(task: EditTask, observers: List<User>?=null){
        title = task.title?:title
        description = task.description?:description
        this.observers = observers?:this.observers
    }
}