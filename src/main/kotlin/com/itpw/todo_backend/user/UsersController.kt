package com.itpw.todo_backend.user

import com.itpw.todo_backend.utils.PagingFormatter
import com.itpw.todo_backend.utils.PagingResponse
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/user")
class UsersController(
    private val usersService: UsersService,
    val pagingFormatter: PagingFormatter
) {

    @GetMapping("/all/")
    fun getAll(
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("page_size", defaultValue = "30") pageSize: Int,
    ): PagingResponse<UserNameResponse> {
        val pageResult = usersService.getPagedUsers(page, pageSize)
        return pagingFormatter.formPagingResponse(pageResult) { UserNameResponse(it) }
    }
}