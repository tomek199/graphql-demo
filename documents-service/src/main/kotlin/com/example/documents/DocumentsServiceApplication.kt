package com.example.documents

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@SpringBootApplication
class DocumentsServiceApplication {
    private val log: Logger = LoggerFactory.getLogger(DocumentsServiceApplication::class.java)
    private val documents: List<Document> = listOf(
        Document(1, "xml", "invoice-1", 1),
        Document(2, "xml", "invoice-2", 2),
        Document(3, "txt", "attachment-1", 3)
    )

    @Bean
    fun router() = router {
        GET("/documents") {
            log.info("Getting documents list")
            ServerResponse.ok().bodyValue(documents)
        }

        GET("/documents/{id}") { request ->
            val id = request.pathVariable("id")
            log.info("Getting document $id")
            documents.find { document -> document.id == id.toInt() } ?.let {
                ServerResponse.ok().bodyValue(it)
            } ?: ServerResponse.notFound().build()
        }
    }
}

data class Document(
    val id: Int,
    val type: String,
    val name: String,
    val contentId: Int,
)

fun main(args: Array<String>) {
    runApplication<DocumentsServiceApplication>(*args)
}
