package com.itpw.todo_backend.authorization

import com.itpw.todo_backend.user.User
import org.springframework.data.repository.CrudRepository

interface AuthenticationUserRepository: CrudRepository<User, Long> {
    fun findByLogin(login: String): User?
}