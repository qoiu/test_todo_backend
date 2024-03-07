package com.itpw.todo_backend.authorization

import com.itpw.todo_backend.user.User
import com.itpw.todo_backend.utils.DetailException
import com.itpw.todo_backend.utils.Translator
import com.itpw.todo_backend.utils.log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationService @Autowired constructor(
    private val userRepository: AuthenticationUserRepository,
    private val jwtSigner: JwtSigner,
    private val translator: Translator
) {

    private val passwordEncoder = BCryptPasswordEncoder()

    fun getUser(login: String) =
        userRepository.findByLogin(login) ?: throw DetailException(translator.toLocale("user_not_found"))

    fun createUser(login: String, password: String): String {
        if (userRepository.findByLogin(login) != null) {
            throw DetailException(translator.toLocale("auth_user_exist"))
        }
        val user = User(login = login, password = passwordEncoder.encode(password))
        userRepository.save(user)
        return jwtSigner.createJwt(user.id.toString())
    }

    fun getToken(login: String, password: String): String {
        val getUser = getUser(login)
        val correctPassword = passwordEncoder.matches(password, getUser.password)
        log.info("haveUser: $correctPassword")
        if (correctPassword) {
            return jwtSigner.createJwt(getUser.id.toString())
        } else {
            throw DetailException(translator.toLocale("login_password_incorrect"))
        }
    }
}