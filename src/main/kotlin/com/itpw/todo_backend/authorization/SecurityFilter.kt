package com.itpw.todo_backend.authorization

import com.itpw.todo_backend.repository.AuthenticationUserRepository
import com.itpw.todo_backend.utils.RequestWithAuth
import com.itpw.todo_backend.utils.log
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class SecurityFilter @Autowired constructor(
    private val tokenProvider: JwtSigner,
    private val userRepository: AuthenticationUserRepository
): OncePerRequestFilter() {
    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return super.shouldNotFilter(request)
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val newRequest = RequestWithAuth(request)
        log.info(request.requestURI)
        log.info(request.requestURL.toString())
        log.info(request.getHeader("Authorization"))
        val authHeader = request.getHeader("Authorization")
        val jwt = authHeader?.replace("Bearer ","")
        log.info("jwt: $jwt")
        if(!jwt.isNullOrBlank()){
            val userId = tokenProvider.getJwtSubject(jwt).toLong()
            newRequest.setAttribute("id", userId)
            log.info("userId: $userId")
            newRequest.addHeader("user-id", userId.toString())
            val userDetails = userRepository.findByIdOrNull(userId)?.let {
                object : UserDetails {
                    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
                        return mutableListOf(SimpleGrantedAuthority("all"))
                    }

                    override fun getPassword(): String {
                        return it.password ?: ""
                    }

                    override fun getUsername(): String {
                        return it.id.toString()
                    }

                    override fun isAccountNonExpired(): Boolean {
                        return true
                    }

                    override fun isAccountNonLocked(): Boolean {
                        return true
                    }

                    override fun isCredentialsNonExpired(): Boolean {
                        return true
                    }

                    override fun isEnabled(): Boolean {
                        return true
                    }
                }
            }
            if (userDetails != null) {
                val authentication = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

                SecurityContextHolder.getContext().authentication = authentication
            }

        }
        filterChain.doFilter(request, response)


    }

}