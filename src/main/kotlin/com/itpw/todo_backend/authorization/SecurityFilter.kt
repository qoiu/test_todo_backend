package com.itpw.todo_backend.authorization

import com.itpw.todo_backend.repository.AuthenticationUserRepository
import com.itpw.todo_backend.utils.log
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class SecurityFilter @Autowired constructor(
    private val tokenProvider: JwtSigner,
//    private val userRepository: AuthenticationUserRepository
): OncePerRequestFilter() {
    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return super.shouldNotFilter(request)
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        log.info(request.requestURI)
        log.info(request.requestURL.toString())
        log.info(request.getHeader("Authorization"))
        val authHeader = request.getHeader("Authorization")
        val jwt = authHeader?.replace("Bearer ","")
        log.info("jwt: $jwt")
        if(!jwt.isNullOrBlank()){
            val userId = tokenProvider.getJwtSubject(jwt).toLong()
            log.info("userId: $userId")

        }


    }

}