package com.itpw.todo_backend.user

import com.fasterxml.jackson.annotation.JsonProperty


class UserNameResponse(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("surname")
    val surname: String
) {
    constructor(user: User) : this(
        id = user.id,
        name = user.name ?: "",
        surname = user.surname ?: ""
    )
}

class EditUser(
    @JsonProperty("name")
    val name: String? = null,
    @JsonProperty("surname")
    val surname: String? = null,
)