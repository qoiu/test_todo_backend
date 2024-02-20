package com.itpw.todo_backend.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.itpw.todo_backend.authorization.AuthenticationService
import com.itpw.todo_backend.authorization.JwtSigner
import com.itpw.todo_backend.models.AuthRequest
import com.itpw.todo_backend.utils.log
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import java.util.*
import java.util.logging.Logger


@RestController
@RequestMapping("/authorization")
class AuthController(
    private val authenticationService: AuthenticationService,
    private val jwtSigner: JwtSigner,
) {
    private val objectMapper = ObjectMapper()

    @PostMapping("/login/")
    fun login(
        @RequestBody request: AuthRequest
    ):String{
        val restTemplate = RestTemplate()
        jwtSigner.setUpSecretKey()
        this@AuthController.log.info("createUser")
        Logger.getLogger("sds").warning("createUser")
        authenticationService.createUser(request.login, request.password)
//        val user = restTemplate.exchange<String>(
//            "http://localhost:8084/user/get_or_create/",
//            HttpMethod.POST,
//            HttpEntity<GetOrCreateProfileRequest>(GetOrCreateProfileRequest(body.phone.filter { it.isDigit() }, body.role))
//        ).body!!
//        val userObj = objectMapper.readValue(
//            user,
//            object : com.fasterxml.jackson.core.type.TypeReference<Map<String, Any>>() {})
//        authenticationService.createUser(request.login)
//        val token = jwtSigner.generateToken(request.login)
        return "get $request"
//        return "get $request -> $token"
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