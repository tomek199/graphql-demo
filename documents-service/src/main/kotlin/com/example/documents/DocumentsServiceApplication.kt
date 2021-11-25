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
        Document("doc-1", "xml", "invoice-1", "con-1", "usr-3"),
        Document("doc-2", "xml", "invoice-2", "con-2", "usr-2"),
        Document("doc-3", "xml", "invoice-3", "con-3", "usr-4"),
        Document("doc-4", "xml", "invoice-4", "con-4", "usr-4"),
        Document("doc-5", "xml", "invoice-5", "con-5", "usr-3"),
        Document("doc-6", "xml", "invoice-6", "con-6", "usr-2"),
        Document("doc-7", "txt", "attachment-7", "con-7", "usr-4"),
        Document("doc-8", "txt", "attachment-8", "con-8", "usr-2"),
        Document("doc-9", "txt", "attachment-9", "con-9", "usr-3"),
        Document("doc-10", "txt", "attachment-10", "con-10", "usr-1"),
        Document("doc-11", "txt", "attachment-11", "con-11", "usr-2"),
        Document("doc-12", "txt", "attachment-12", "con-12", "usr-1"),
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
            documents.find { document -> document.id == id } ?.let {
                ServerResponse.ok().bodyValue(it)
            } ?: ServerResponse.notFound().build()
        }

        GET("/users/{id}/documents") { request ->
            val id = request.pathVariable("id")
            log.info("Getting documents list for user $id")
            documents.filter { document -> document.userId == id } .let {
                ServerResponse.ok().bodyValue(it)
            }
        }
    }
}

data class Document(
    val id: String,
    val type: String,
    val name: String,
    val contentId: String,
    val userId: String,
)

fun main(args: Array<String>) {
    runApplication<DocumentsServiceApplication>(*args)
}
