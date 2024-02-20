package com.itpw.todo_backend.authorization

import com.itpw.todo_backend.controllers.user.User
import com.itpw.todo_backend.repository.AuthenticationUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthenticationService @Autowired constructor(
//    private val otcRequestsService: OtcRequestsService,
    private val userRepository: AuthenticationUserRepository,
    private val jwtSigner: JwtSigner,
//    private val authenticationUserProvider: AuthenticationUserProvider
) {

    fun createUser(name: String, password: String): User{
        return userRepository.save(User(login = name, password = password))
    }

    fun findUser(login: String): User?{
        return userRepository.findByLogin(login)
    }


//
//    fun requestOtc(phone: String): DetailsResponse {
//        val response = otcRequestsService.requestOtc(phone.filter { it.isDigit() })
//        return DetailsResponse(response)
//    }
//
//    fun checkOtcAuthentication(authenticationByPhoneRequest: AuthenticationByPhoneRequest): Boolean {
//        return otcRequestsService.checkCode(authenticationByPhoneRequest.phone.filter { it.isDigit() }, authenticationByPhoneRequest.otc ?: "")
//    }
//
//    fun loginByPhone(request: AuthenticationByPhoneRequest): Any {
//        return if (request.otc == null) {
//            requestOtc(request.phone)
//        } else {
//            checkOtcAuthentication(request)
//            var user = userRepository.findByPhoneAndIsDeletedFalseAndRole(request.phone.filter { it.isDigit() }, request.role ?: UserRole.USER.name)
//            if (user == null) {
//                user = authenticationUserProvider.createUser(
//                    AuthenticationUser(
//                        phone = request.phone.filter { it.isDigit() },
//                        role = request.role ?: UserRole.USER.name,
//                        isVerified = false
//                    )
//                )
//            }
//            val token = jwtSigner.createJwt(user.id.toString())
//            val response = AuthenticationResponse(
//                token = token,
//                user = AuthenticatedUser(
//                    id = user.id,
//                    role = user.role,
//                    name = user.name,
//                    surname = user.surname,
//                    avatar = user.avatar,
//                    phone = user.phone
//                )
//            )
//            response
//        }
//    }
//
//    fun registerByPassword(request: AuthenticationByPasswordRequest): DetailsResponse {
//        if (userRepository.existsByLoginAndIsDeletedFalse(request.login)) {
//            throw DetailException("Пользователь с таким логином уже существует")
//        }
//        val user = authenticationUserProvider.createUser(
//            AuthenticationUser(
//                login = request.login,
//                password = passwordEncoder.encode(request.password),
//                role = request.role.name,
//                isVerified = request.role == UserRole.USER
//            )
//        )
//        return DetailsResponse("Пользователь зарегистрирован")
//    }
//
//    fun loginByPassword(request: AuthenticationByPasswordRequest): AuthenticationResponse {
//        val user = userRepository.findByLoginAndIsDeletedFalse(request.login)
//            ?: throw DetailException("Неправильный логин или пароль")
//        if (!passwordEncoder.matches(request.password, user.password)) {
//            throw DetailException("Неправильный логин или пароль")
//        }
//        if (!user.isVerified) {
//            throw ForbiddenException("Аккаунт не подтверждён")
//        }
//        return AuthenticationResponse(
//            token = jwtSigner.createJwt(user.id.toString()),
//            user = AuthenticatedUser(
//                id = user.id,
//                role = user.role,
//                name = user.name,
//                surname = user.surname,
//                avatar = user.avatar,
//                phone = user.phone
//            )
//        )
//    }
}