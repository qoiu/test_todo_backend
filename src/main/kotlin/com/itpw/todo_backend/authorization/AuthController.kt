package com.itpw.todo_backend.authorization

import com.itpw.todo_backend.user.UserNameResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
@RequestMapping("/authorization")
class AuthController(
    private val authenticationService: AuthenticationService,
) {

    @PostMapping("/login/")
    fun login(
        @RequestBody request: AuthRequest
    ): AuthResponse {
        val user = authenticationService.getUser(request.login)
        val token = authenticationService.getToken(request.login, request.password)
        return AuthResponse(UserNameResponse(user), token)
    }

    @PostMapping("/create/")
    fun create(
        @RequestBody request: AuthRequest
    ): AuthResponse {
        val token = authenticationService.createUser(request.login, request.password)
        val user = authenticationService.getUser(request.login)
        return AuthResponse(UserNameResponse(user), token)
    }
}