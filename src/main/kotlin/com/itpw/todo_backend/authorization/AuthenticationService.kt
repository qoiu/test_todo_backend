package com.itpw.todo_backend.authorization

import com.itpw.todo_backend.controllers.task.Task
import com.itpw.todo_backend.controllers.user.User
import com.itpw.todo_backend.repository.AuthenticationUserRepository
import com.itpw.todo_backend.utils.DetailException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AuthenticationService @Autowired constructor(
//    private val otcRequestsService: OtcRequestsService,
    private val userRepository: AuthenticationUserRepository,
    private val jwtSigner: JwtSigner,
//    private val authenticationUserProvider: AuthenticationUserProvider
) {

    fun getPagedUsers(page: Int, pageSize: Int): Page<User> {
        val user = userRepository.findAll()
        val pageable = PageRequest.of(page, pageSize, Sort.by("id"))
        return userRepository.findAll(pageable)
    }

    fun findUser(login: String): User?{
        return userRepository.findByLogin(login)
    }
}