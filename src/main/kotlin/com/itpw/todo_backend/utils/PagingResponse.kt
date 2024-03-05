package com.itpw.todo_backend.utils

import com.fasterxml.jackson.annotation.JsonProperty

data class PagingResponse<T: Any>(
    @JsonProperty("count")
    val count: Long,
    @JsonProperty("previous")
    val previous: String?,
    @JsonProperty("next")
    val next: String?,
    @JsonProperty("results")
    val results: List<T>
)
