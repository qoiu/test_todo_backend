package com.itpw.todo_backend.controllers

import com.itpw.todo_backend.TodoBackendApplication
import com.itpw.todo_backend.utils.log
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.logging.LogManager
import java.util.logging.Logger

@RestController
class TestController {

    @GetMapping("/hello/")
    fun hello():String{
        TodoBackendApplication.log.error("Hello world")
        log.info("Hello world").log
        Logger.getLogger("dff").info("backend log")
        LogManager.getLogManager().getLogger("dsds").info("dssdds")
        System.out.println("job")
        println("Hello world")
        return "Hello world"
    }
}