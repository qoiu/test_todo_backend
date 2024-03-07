package com.itpw.todo_backend.task

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository

interface TasksRepository: CrudRepository<Task, Long>, PagingAndSortingRepository<Task, Long> {
fun findByOwner_IdAndIsDeletedFalseAndIsFinished(id: Long, isFinished: Boolean):List<Task>

fun findByOwner_IdAndIsDeletedFalseAndIsFinished(id: Long, isFinished: Boolean, pageable: Pageable,): Page<Task>

fun findByObservers_IdAndIsDeletedFalseAndIsFinished(id: Long, isFinished: Boolean, pageable: Pageable,): Page<Task>
}