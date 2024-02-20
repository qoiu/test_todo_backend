package com.itpw.todo_backend.controllers.task

import com.itpw.todo_backend.controllers.user.User
import com.itpw.todo_backend.models.AuthRequest
import com.itpw.todo_backend.repository.TasksRepository
import com.itpw.todo_backend.utils.log
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/task")
class TaskController(
    val taskService: TaskService
    ) {


    @PostMapping("/mock/")
    fun mock(
        @RequestHeader("Authorization") authorization: String,
        @RequestAttribute id: Long
        ){
        log.info(authorization)
        log.info("authorization id: $id")

        log.info("authorization: ${SecurityContextHolder.getContext().authentication}")
        taskService.createTask(id)
    }

    @GetMapping("/")
    fun getAll(
        @RequestHeader("Authorization") authorization: String,
        @RequestAttribute id: Long
        ): List<TaskResponse>{
        log.info("authorization id: $id")
        return taskService.getTasks(id).map { TaskResponse(it) }
    }
}