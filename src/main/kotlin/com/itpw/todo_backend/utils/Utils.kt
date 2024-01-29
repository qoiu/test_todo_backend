package com.itpw.todo_backend.utils

import java.util.logging.Logger

val Any.log: Logger
    get() = Logger.getLogger(this::class.java.simpleName)