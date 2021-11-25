package com.example.contents

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@SpringBootApplication
class ContentsServiceApplication {
    private val log: Logger = LoggerFactory.getLogger(ContentsServiceApplication::class.java)
    private val contents: List<Content> = listOf(
        Content(1, "<element>I'm xml</element>", 26),
        Content(2, "<element>I'm also xml</element>", 31),
        Content(3, "I am static text", 16)
    )

    @Bean
    fun router() = router {
        GET("/contents") {
            log.info("Getting contents list")
            ServerResponse.ok().bodyValue(contents)
        }

        GET("/contents/{id}") { request ->
            val id = request.pathVariable("id")
            log.info("Getting content $id")
            contents.find { content -> content.id == id.toInt() } ?.let {
                ServerResponse.ok().bodyValue(it)
            } ?: ServerResponse.notFound().build()
        }
    }
}

data class Content(
    val id: Int,
    val body: String,
    val size: Int
)

fun main(args: Array<String>) {
    runApplication<ContentsServiceApplication>(*args)
}