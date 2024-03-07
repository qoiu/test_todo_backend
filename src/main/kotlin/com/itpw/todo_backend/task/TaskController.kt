package com.itpw.todo_backend.task

import com.itpw.todo_backend.utils.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/task")
class TaskController @Autowired constructor(
    val taskService: TaskService,
    val pagingFormatter: PagingFormatter,
    private val translator: Translator
) {


    @PostMapping("/mock/")
    fun mock(
        authentication: Authentication,
    ) {
        taskService.createTask(authentication.name.toLong(), EditTask("test task"))
    }

    @PostMapping("/")
    fun create(
        authentication: Authentication,
        @RequestBody body: EditTask
    ): DetailsResponse {
        taskService.createTask(authentication.name.toLong(), body)
        return DetailsResponse(translator.toLocale("task_created_success"))
    }

    @PatchMapping("/")
    fun edit(
        authentication: Authentication,
        @RequestParam("id") id: Long,
        @RequestBody body: EditTask
    ): TaskResponse {
        val task = taskService.editTask(authentication.name.toLong(), id, body)
        return TaskResponse(task)
    }

    @GetMapping("/all/")
    fun getAll(
        authentication: Authentication,
    ): List<TaskResponse> {
        return taskService.getTasks(authentication.name.toLong()).map { TaskResponse(it) }
    }

    @GetMapping("/mine/")
    fun getMine(
        authentication: Authentication,
    ): List<TaskResponse> {
        return taskService.getTasks(authentication.name.toLong()).map { TaskResponse(it) }
    }

    @GetMapping("/watched/")
    fun getWatched(
        authentication: Authentication,
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("page_size", defaultValue = "30") pageSize: Int,
    ): PagingResponse<TaskResponse> {
        return pagingFormatter.formPagingResponse(taskService.getWatchedTasks(authentication.name.toLong(), page, pageSize)){ TaskResponse(it) }
    }

    @GetMapping("/")
    fun getPagedTasks(
        authentication: Authentication,
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("page_size", defaultValue = "30") pageSize: Int,
    ): PagingResponse<TaskResponse> {
        val pageResult = taskService.getTasks(authentication.name.toLong(), page, pageSize)

        return pagingFormatter.formPagingResponse(pageResult) { TaskResponse(it) }
    }

    @PostMapping("/finish/{id}/")
    fun finishTask(
        authentication: Authentication,
        @PathVariable("id") taskId: Long
    ): DetailsResponse {
        taskService.finishTask(authentication.name.toLong(), taskId)
        return DetailsResponse(translator.toLocale("task_finished_success"))
    }


}