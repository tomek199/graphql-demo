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
        Content("con-1", "<element>I'm xml</element>", 26),
        Content("con-2", "<element>I'm xml</element>", 26),
        Content("con-3", "<element>I'm xml</element>", 26),
        Content("con-4", "<element>I'm also xml</element>", 31),
        Content("con-5", "<element>I'm also xml</element>", 31),
        Content("con-6", "<element>I'm also xml</element>", 31),
        Content("con-7", "<element>I'm also xml</element>", 31),
        Content("con-8", "I am static text", 16),
        Content("con-9", "I am static text", 16),
        Content("con-10", "I am static text", 16),
        Content("con-11", "I am static text", 16),
        Content("con-12", "I am static text", 16)
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
            contents.find { content -> content.id == id } ?.let {
                ServerResponse.ok().bodyValue(it)
            } ?: ServerResponse.notFound().build()
        }
    }
}

data class Content(
    val id: String,
    val body: String,
    val size: Int
)

fun main(args: Array<String>) {
    runApplication<ContentsServiceApplication>(*args)
}