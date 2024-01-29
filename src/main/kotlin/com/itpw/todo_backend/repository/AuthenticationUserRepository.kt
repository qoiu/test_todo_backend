package com.itpw.todo_backend.repository

import com.itpw.todo_backend.authorization.AuthenticationUser
import org.springframework.data.repository.CrudRepository

interface AuthenticationUserRepository: CrudRepository<AuthenticationUser, Long> {
}