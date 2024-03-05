package com.itpw.todo_backend.utils

import org.springframework.data.domain.Page
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

class PagingFormatter {
    fun <T: Any, R: Any>formPagingResponse(page: Page<T>, mapper: (T) -> R): PagingResponse<R> {
        val previous = if (page.hasPrevious()) {
            ServletUriComponentsBuilder.fromCurrentRequest()
                .apply {
                    replaceQueryParam("page", (page.number - 1).toString())
                }
                .encode()
                .toUriString()
        } else {
            null
        }
        val next = if (page.hasNext()) {
            ServletUriComponentsBuilder.fromCurrentRequest()
                .apply {
                    replaceQueryParam("page", (page.number + 1).toString())
                }
                .encode()
                .toUriString()
        } else {
            null
        }
        return PagingResponse(
            count = page.totalElements,
            previous = previous,
            next = next,
            results = page.content.map {
                mapper(it)
            }
        )
    }
}