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
    val id: Long,
    @JsonProperty
    val name: String,
    @JsonProperty
    val surname: String
){
    constructor(user: User):this(id=user.id, name=user.name?:"", surname=user.surname?:"")
}

class EditUser(
    @JsonProperty
    val name: String? = null,
    @JsonProperty
    val surname: String? = null,
)