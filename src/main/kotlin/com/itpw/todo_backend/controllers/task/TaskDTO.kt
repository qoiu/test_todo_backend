package com.itpw.todo_backend.controllers.task

import com.fasterxml.jackson.annotation.JsonProperty
import com.itpw.todo_backend.controllers.user.User
import com.itpw.todo_backend.controllers.user.UserNameResponse
import jakarta.persistence.ManyToOne
import jakarta.persistence.Temporal
import jakarta.persistence.TemporalType
import java.util.*

data class TaskResponse(
    @JsonProperty
    val id: Long = -1L,
    @JsonProperty
    var title: String? = null,
    @JsonProperty
    var description: String? = null,
    @JsonProperty
    var startTime: Calendar = Calendar.getInstance(),
    @JsonProperty
    var owner: UserNameResponse,
    @JsonProperty
    var watchers: List<UserNameResponse>,
) {
    constructor(task: Task) : this(
        id = task.id,
        title = task.title,
        description = task.description,
        startTime = task.startTime,
        owner = UserNameResponse(task.owner),
        watchers = task.observers.map { UserNameResponse(it) }
    )
}

data class EditTask(
    val title: String? = null,
    val description: String? = null,
    val watchers: List<Long>? = null
){
    fun toTask(owner: User, observers: List<User>) = Task(
        title = this.title?:"",
        description = this.description?:"",
        owner = owner,
        observers = observers
    )
}