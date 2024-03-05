package com.itpw.todo_backend.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.itpw.todo_backend.authorization.AuthenticationService
import com.itpw.todo_backend.authorization.JwtSigner
import com.itpw.todo_backend.controllers.user.UserResponse
import com.itpw.todo_backend.models.AuthRequest
import com.itpw.todo_backend.utils.DetailException
import com.itpw.todo_backend.utils.log
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import java.util.*


@RestController
@RequestMapping("/authorization")
class AuthController(
    private val authenticationService: AuthenticationService,
    private val jwtSigner: JwtSigner,
) {
    private val objectMapper = ObjectMapper()

    private val passwordEncoder = BCryptPasswordEncoder()

    @PostMapping("/login/")
    fun login(
        @RequestBody request: AuthRequest
    ):UserResponse{
        log.info("createUser: ${request.login} - ${passwordEncoder.encode(request.password)}")
        val getUser = authenticationService.findUser(request.login)
        return if(getUser!=null) {
            val correctPassword = passwordEncoder.matches(request.password, getUser.password)
            log.info("haveUser: $correctPassword")
            if(correctPassword){
                val token = jwtSigner.createJwt(getUser!!.id.toString())
                UserResponse(request.login, token!!)
            }else {
                throw DetailException("Неправильный логин или пароль")
            }
        }else {
            throw DetailException("Неправильный логин или пароль")
//            log.info("find: $getUser")
//            val user = authenticationService.createUser(request.login, passwordEncoder.encode(request.password))
//            val token = jwtSigner.createJwt(user.id.toString())
//            UserResponse(request.login, token)
        }
    }

//    @PostMapping("/login/")
//    fun login(
//        @RequestBody request: AuthRequest
//    ):String{
//        setUpSecretKey()
//        val token = generateToken(request.login)
//        return "get $request -> $token"
//    }
//
//    @PostMapping("/user/")
//    fun user(
//        @RequestHeader("Authorization") auth: String,
//        @RequestBody request: AuthRequest
//    ):String{
//        setUpSecretKey()
//        val token = generateToken(request.login)
//        return "get $request -> $token"
//    }
}