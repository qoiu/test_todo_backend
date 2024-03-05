package com.itpw.todo_backend.controllers.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.itpw.todo_backend.authorization.AuthenticationService
import com.itpw.todo_backend.authorization.JwtSigner
import com.itpw.todo_backend.controllers.task.TaskResponse
import com.itpw.todo_backend.controllers.user.UserResponse
import com.itpw.todo_backend.models.AuthRequest
import com.itpw.todo_backend.utils.DetailException
import com.itpw.todo_backend.utils.PagingFormatter
import com.itpw.todo_backend.utils.PagingResponse
import com.itpw.todo_backend.utils.log
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import java.util.*


@RestController
@RequestMapping("/user")
class UsersController(
    private val authenticationService: AuthenticationService,
    val pagingFormatter: PagingFormatter
) {

    @GetMapping("/all/")
    fun getAll(
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("page_size", defaultValue = "30") pageSize: Int,
    ): PagingResponse<UserNameResponse> {
        val pageResult = authenticationService.getPagedUsers(page, pageSize)
        return pagingFormatter.formPagingResponse(pageResult) { UserNameResponse(it) }
    }
}