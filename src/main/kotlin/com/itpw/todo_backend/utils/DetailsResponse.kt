package com.itpw.todo_backend.utils

import com.fasterxml.jackson.annotation.JsonProperty

class DetailsResponse(@JsonProperty("details") val details: String)