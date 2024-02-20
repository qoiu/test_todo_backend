package com.itpw.todo_backend.repository

import com.itpw.todo_backend.controllers.user.User
import org.springframework.data.repository.CrudRepository

interface AuthenticationUserRepository: CrudRepository<User, Long> {
    fun findByLogin(login: String): User?
}