package com.itpw.todo_backend.controllers

import com.itpw.todo_backend.authorization.AuthenticationService
import com.itpw.todo_backend.authorization.JwtSigner
import com.itpw.todo_backend.models.AuthRequest
import com.itpw.todo_backend.utils.log
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import java.io.UnsupportedEncodingException
import java.time.Duration
import java.time.Instant
import java.util.*
import java.util.logging.Logger
import javax.crypto.SecretKey


@RestController
class AuthController(
    private val authenticationService: AuthenticationService,
    private val jwtSigner: JwtSigner,
) {

    @PostMapping("/authorization/")
    fun authorization(
        @RequestBody request: AuthRequest
    ):String{
//        jwtSigner.setUpSecretKey()
        "sds".log.info("createUser")
        Logger.getLogger("sds").info("createUser")
        authenticationService.createUser(request.login)
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