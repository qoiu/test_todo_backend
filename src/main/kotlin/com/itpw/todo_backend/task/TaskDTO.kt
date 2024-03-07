package com.itpw.todo_backend.task

import com.fasterxml.jackson.annotation.JsonProperty
import com.itpw.todo_backend.user.UserNameResponse
import java.util.*

data class TaskResponse(
    @JsonProperty("id")
    val id: Long = -1L,
    @JsonProperty("title")
    var title: String? = null,
    @JsonProperty("description")
    var description: String? = null,
    @JsonProperty("start_time")
    var startTime: Calendar = Calendar.getInstance(),
    @JsonProperty("owner")
    var owner: UserNameResponse,
    @JsonProperty("watchers")
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
    @JsonProperty("title")
    val title: String? = null,
    @JsonProperty("description")
    val description: String? = null,
    @JsonProperty("watchers")
    val watchers: List<Long>? = null
)