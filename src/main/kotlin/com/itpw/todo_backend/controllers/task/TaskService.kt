package com.itpw.todo_backend.controllers.task

import com.itpw.todo_backend.repository.AuthenticationUserRepository
import com.itpw.todo_backend.repository.TasksRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TaskService @Autowired constructor(
    val tasksRepository: TasksRepository,
                                         private val userService: AuthenticationUserRepository,
    ) {


    fun createTask(userId: Long){
        val user = userService.findByIdOrNull(userId)
        if(user!=null)
        tasksRepository.save(Task(owner = user))
    }

    fun getTasks(id: Long):List<Task>{
        val user = userService.findByIdOrNull(id) ?: return emptyList()
        return tasksRepository.findByOwner(user)
    }
}