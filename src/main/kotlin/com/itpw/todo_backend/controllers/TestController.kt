package com.itpw.todo_backend.controllers

import com.itpw.todo_backend.TodoBackendApplication
import com.itpw.todo_backend.authorization.AuthenticationService
import com.itpw.todo_backend.repository.TasksRepository
import com.itpw.todo_backend.utils.log
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.logging.LogManager
import java.util.logging.Logger

@RestController
class TestController(
) {

    @GetMapping("/hello/")
    fun hello():String{
        Logger.getLogger("dff").info("backend log")
        return "Hello world"
    }
}