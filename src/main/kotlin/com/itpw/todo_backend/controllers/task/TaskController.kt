package com.itpw.todo_backend.controllers.task

import com.itpw.todo_backend.controllers.user.User
import com.itpw.todo_backend.models.AuthRequest
import com.itpw.todo_backend.repository.TasksRepository
import com.itpw.todo_backend.utils.PagingFormatter
import com.itpw.todo_backend.utils.PagingResponse
import com.itpw.todo_backend.utils.log
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.Query
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/task")
class TaskController @Autowired constructor(
    val taskService: TaskService,
    val pagingFormatter: PagingFormatter
    ) {


    @PostMapping("/mock/")
    fun mock(
        @RequestHeader("user-id") userId: String,
        ){
        log.info("authorization: $userId")
        taskService.createTask(userId.toLong(), EditTask("test task"))
    }

    @PostMapping("/")
    fun create(
        @RequestHeader("user-id") userId: Long,
        @RequestBody body: EditTask
        ){
        log.info("authorization: $userId")
        taskService.createTask(userId, body)
    }

    @PatchMapping("/")
    fun edit(
        @RequestHeader("user-id") userId: Long,
        @RequestParam("id") id: Long,
        @RequestBody body: EditTask
        ){
        log.info("authorization: $userId")
        taskService.editTask(userId, id, body)
    }

    @GetMapping("/all/")
    fun getAll(
        @RequestHeader("user-id") userId: Long,
        ): List<TaskResponse> {
        log.info("authorization id: $userId")
        return taskService.getTasks(userId).map { TaskResponse(it) }
    }

    @GetMapping("/")
    fun getPagedTasks(
        @RequestHeader("user-id") userId: Long,
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("page_size", defaultValue = "30") pageSize: Int,
        ): PagingResponse<TaskResponse> {
        log.info("authorization id: $userId")
        val pageResult = taskService.getTasks(userId,page,pageSize)
        return pagingFormatter.formPagingResponse(pageResult) { TaskResponse(it) }
    }

    @DeleteMapping("/{id}/")
    fun deleteTask(
        @RequestHeader("user-id") userId: Long,
        @PathVariable("id") taskId: Long
    ){
        return taskService.deleteTask(userId,taskId)
    }


}