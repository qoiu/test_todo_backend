package com.itpw.todo_backend.authorization

import com.fasterxml.jackson.annotation.JsonProperty
import com.itpw.todo_backend.user.UserNameResponse

data class AuthRequest(
    @JsonProperty("login")
    val login: String,
    @JsonProperty("password")
    val password: String,

)

class AuthResponse(
    @JsonProperty("user")
    val login: UserNameResponse,
    @JsonProperty("token")
    val token: String
)
