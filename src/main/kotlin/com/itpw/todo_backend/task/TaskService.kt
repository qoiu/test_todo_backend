package com.itpw.todo_backend.task

import com.itpw.todo_backend.user.UserRepository
import com.itpw.todo_backend.utils.DetailException
import com.itpw.todo_backend.utils.ForbiddenException
import com.itpw.todo_backend.utils.Translator
import com.itpw.todo_backend.utils.log
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Root
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class TaskService @Autowired constructor(
    val tasksRepository: TasksRepository,
    private val userRepository: UserRepository,
    private val translator: Translator
) {

    private fun getTask(taskId: Long): Task {
        return tasksRepository.findByIdOrNull(taskId) ?: throw DetailException(translator.toLocale("task_not_fond"))
    }

    private fun getObservers(task: EditTask) =
        task.watchers?.let { userRepository.findAllById(it).toMutableList() }?: mutableListOf()

    private fun getUser(userId: Long) =
        userRepository.findByIdOrNull(userId) ?: throw DetailException(translator.toLocale("user_not_fond"))

    fun createTask(userId: Long, task: EditTask) {
        val user = getUser(userId)
        val observers = getObservers(task)
        log.info("task.watchers: ${task.watchers}")
        log.info("observers: $observers")
        tasksRepository.save(
            Task(
                title = task.title ?: "",
                description = task.description ?: "",
                owner = user,
                observers = observers
            )
        )
    }


    fun getTasks(userId: Long, isDeleted: Boolean = false, isFinished: Boolean = false): List<Task> {
        val user = getUser(userId)
        return tasksRepository.findByOwner_IdAndIsDeletedFalseAndIsFinished(userId, isFinished)
    }

    fun getTasks(
        userId: Long,
        page: Int,
        pageSize: Int,
        isDeleted: Boolean = false,
        isFinished: Boolean = false
    ): Page<Task> {
        val user = getUser(userId)
        val pageable = PageRequest.of(page, pageSize, Sort.by("title", "id"))
        return tasksRepository.findByOwner_IdAndIsDeletedFalseAndIsFinished(userId, isFinished, pageable)
    }


    fun tasksByObserverId(userId: Long): Specification<Task?>? {
        return Specification { root: Root<Task?>, query: CriteriaQuery<*>?, criteriaBuilder: CriteriaBuilder ->
            criteriaBuilder.isMember(
                userId,
                root.get("observers")
            )
        }
    }

    fun getWatchedTasks(
        userId: Long,
        page: Int,
        pageSize: Int,
        isDeleted: Boolean = false,
        isFinished: Boolean = false
    ): Page<Task> {
        val user = getUser(userId)
        val pageable = PageRequest.of(page, pageSize, Sort.by("title", "id"))

//        return tasksRepository.findAll(pageable)
        return tasksRepository.findByObservers_IdAndIsDeletedFalseAndIsFinished(userId, isFinished, pageable)
    }


    fun editTask(userId: Long, taskId: Long, editTask: EditTask): Task {
        val task = getTask(taskId)
        if (task.owner.id != userId) throw ForbiddenException(translator.toLocale("task_edit_owner_permission"))
        val users = editTask.watchers?.let { userRepository.findAllById(it) }?.toMutableList() ?: mutableListOf()
        task.apply {
            title = task.title
            description = task.description
            observers = users

        }
        tasksRepository.save(task)
        return task
    }

    fun finishTask(userId: Long, taskId: Long) {
        val task = getTask(taskId)
        if (task.owner.id != userId) {
            throw ForbiddenException(translator.toLocale("task_finish_owner_permission"))
        }
        task.isFinished = true
        tasksRepository.save(task)
        return
    }
}