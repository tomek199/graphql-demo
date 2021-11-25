package com.example.users

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@SpringBootApplication
class UsersServiceApplication {
    private val log: Logger = LoggerFactory.getLogger(UsersServiceApplication::class.java)
    private val users: List<User> = listOf(
        User(1, "Optimus Prime", "optimus@mail.com", "Transformers"),
        User(2, "Han Solo", "solo@mail.com", "Star Wars"),
        User(3, "Yoda", "yoda@mail.com", "Star Wars"),
        User(4, "Bumblebee", "bumblebee@mail.com", "Transformers"),
        User(5, "Luke Skywalker", "luke@mail.com", "Star Wars")
    )

    @Bean
    fun router() = router {
        GET("/users") {
            log.info("Getting users list")
            ServerResponse.ok().bodyValue(users)
        }

        GET("/users/{id}") { request ->
            val id = request.pathVariable("id")
            log.info("Getting user $id")
            users.find { user -> user.id == id.toInt() }?.let {
                ServerResponse.ok().bodyValue(it)
            } ?: ServerResponse.notFound().build()
        }
    }
}

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val company: String
)

fun main(args: Array<String>) {
    runApplication<UsersServiceApplication>(*args)
}