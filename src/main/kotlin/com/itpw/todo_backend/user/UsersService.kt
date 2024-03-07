package com.itpw.todo_backend.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class UsersService @Autowired constructor(
    private val userRepository: UserRepository,
) {

    fun getPagedUsers(page: Int, pageSize: Int): Page<User> {
        val pageable = PageRequest.of(page, pageSize, Sort.by("id"))
        return userRepository.findAll(pageable)
    }
}