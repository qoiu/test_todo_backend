package com.itpw.todo_backend.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Logger

@RestController
class TestController {

    @GetMapping("/hello/")
    fun hello():String{
        Logger.getLogger("dff").info("backend log")
        return "Hello world"
    }
}