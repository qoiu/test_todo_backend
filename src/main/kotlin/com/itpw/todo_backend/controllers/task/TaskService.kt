package com.itpw.todo_backend.controllers.task

import com.itpw.todo_backend.controllers.user.User
import com.itpw.todo_backend.repository.AuthenticationUserRepository
import com.itpw.todo_backend.repository.TasksRepository
import com.itpw.todo_backend.utils.DetailException
import com.itpw.todo_backend.utils.ForbiddenException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException.Forbidden

@Service
class TaskService @Autowired constructor(
    val tasksRepository: TasksRepository,
                                         private val userService: AuthenticationUserRepository,
    ) {


    fun createTask(userId: Long, task: EditTask){
        val user = userService.findByIdOrNull(userId)
        val observers = task.watchers?.let {  userService.findAllById(it) }?.toList()?: emptyList()
        if(user!=null)
        tasksRepository.save(task.toTask(owner = user, observers = observers))
    }

    fun getTask(taskId: Long): Task{
        return tasksRepository.findByIdOrNull(taskId)?: throw DetailException("Задача не найдена")
    }

    fun getTasks(userId: Long, isDeleted: Boolean = false, isFinished: Boolean = false):List<Task>{
        val user = userService.findByIdOrNull(userId) ?: return emptyList()
        return tasksRepository.findByOwnerAndIsDeletedAndIsFinished(user, isDeleted, isFinished)
    }

    fun getTasks(userId: Long, page: Int, pageSize: Int, isDeleted: Boolean = false, isFinished: Boolean = false): Page<Task> {
        val user = userService.findByIdOrNull(userId) ?: throw DetailException("Пользователь не найден")
        val pageable = PageRequest.of(page, pageSize, Sort.by("title", "id"))
        return tasksRepository.findByOwnerAndIsDeletedAndIsFinished(user,pageable, isDeleted, isFinished)
    }

    fun editTask(userId: Long, taskId: Long, editTask: EditTask){
        val task = getTask(taskId)
        if(task.owner.id!=userId)throw ForbiddenException("Только создатель задачи может её редактироваить")
        val users = editTask.watchers?.let {  userService.findAllById(it) }?.toList()
        task.update(editTask, users)
        tasksRepository.save(task)
    }

    fun deleteTask(userId: Long, taskId: Long) {
        val user = userService.findByIdOrNull(userId) ?: throw DetailException("Пользователь не найден")
        val task = getTask(taskId)
        if(task.owner.id!=userId)throw ForbiddenException("Только создатель задачи может её завершить")
        return
    }
}