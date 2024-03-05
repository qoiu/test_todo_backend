package com.itpw.todo_backend.repository

import com.itpw.todo_backend.authorization.AuthenticationUser
import com.itpw.todo_backend.controllers.task.Task
import com.itpw.todo_backend.controllers.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository

interface TasksRepository: CrudRepository<Task, Long> {
fun findByOwnerAndIsDeletedAndIsFinished(owner: User, isDeleted: Boolean, isFinished: Boolean):List<Task>
fun findByOwnerAndIsDeletedAndIsFinished(owner: User, pageable: Pageable, isDeleted: Boolean, isFinished: Boolean): Page<Task>
}