package com.itpw.todo_backend.repository

import com.itpw.todo_backend.authorization.AuthenticationUser
import com.itpw.todo_backend.controllers.task.Task
import com.itpw.todo_backend.controllers.user.User
import org.springframework.data.repository.CrudRepository

interface TasksRepository: CrudRepository<Task, Long> {
fun findByOwner(owner: User):List<Task>
}