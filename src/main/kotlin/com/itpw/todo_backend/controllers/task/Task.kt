package com.itpw.todo_backend.controllers.task

import com.fasterxml.jackson.annotation.JsonProperty
import com.itpw.todo_backend.controllers.user.User
import jakarta.persistence.*
import org.hibernate.annotations.Formula
import java.time.LocalDate
import java.util.*

@Entity
@Table(
    name = "tasks"
)
class Task (
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY,
    )
    val id: Long = -1L,
    var title: String? = null,
    var description: String? = null,
    @Temporal(TemporalType.TIMESTAMP)
    var startTime: Calendar = Calendar.getInstance(),
    @ManyToOne
    var owner: User=User(),
//    @ManyToMany(cascade = [CascadeType.ALL], mappedBy = "id")
//    var watching: List<User>
)