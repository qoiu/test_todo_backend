package com.itpw.todo_backend.user

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository

interface UserRepository: CrudRepository<User, Long>, PagingAndSortingRepository<User, Long>