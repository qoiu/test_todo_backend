package com.itpw.todo_backend.utils

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import java.util.*


class RequestWithAuth(request: HttpServletRequest) : HttpServletRequestWrapper(request) {
    private val additionalHeaders = mutableMapOf<String, String>()

    override fun getHeader(name: String): String {
        val header = if (additionalHeaders.containsKey(name)) {
            additionalHeaders[name]
        } else {
            super.getHeader(name)
        }
        return header ?: ""
    }

    override fun getHeaderNames(): Enumeration<String> {
        val names: MutableList<String> = (Collections.list(super.getHeaderNames()) + additionalHeaders.keys.toMutableList()).toMutableList()
        names.addAll(Collections.list(super.getParameterNames()))
        return Collections.enumeration(names)
    }

    override fun getHeaders(name: String): Enumeration<String> {
        val headers = if (additionalHeaders.containsKey(name)) {
            Collections.enumeration(listOf(additionalHeaders[name]))
        } else {
            super.getHeaders(name)
        }
        return headers
    }

    fun addHeader(name: String, value: String) {
        additionalHeaders[name] = value
    }
}