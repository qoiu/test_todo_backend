package com.itpw.todo_backend.controllers

@RestController
class TestController {

    @GetMapping("/hello/")
    fun hello():String{
        return "Hello world"
    }
}