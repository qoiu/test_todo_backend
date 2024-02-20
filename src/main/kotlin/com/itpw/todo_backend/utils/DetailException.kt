package com.itpw.todo_backend.utils

import com.fasterxml.jackson.annotation.JsonProperty

class DetailException(final val details: String): Exception()

class DetailsResponse(@JsonProperty val details: String)