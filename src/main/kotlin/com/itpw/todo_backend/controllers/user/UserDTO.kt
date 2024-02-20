package com.itpw.todo_backend.controllers.user

import com.fasterxml.jackson.annotation.JsonProperty


class UserResponse(
    @JsonProperty
    val login: String,
    @JsonProperty
    val token: String
)

class UserNameResponse(
    @JsonProperty
    val name: String,
    @JsonProperty
    val surname: String
){
    constructor(user: User):this(name=user.name?:"", surname=user.surname?:"")
}