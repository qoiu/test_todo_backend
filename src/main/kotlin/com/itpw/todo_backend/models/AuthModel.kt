package com.itpw.todo_backend.models

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthRequest(
    @JsonProperty("login")
    val login: String,
    @JsonProperty("password")
    val password: String,

)
