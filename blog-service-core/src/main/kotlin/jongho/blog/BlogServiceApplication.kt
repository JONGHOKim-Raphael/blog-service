package jongho.blog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

@SpringBootApplication
class BlogServiceApplication

fun main(args: Array<String>) {
    log.info { "---HELLO, BLOG-SERVICE !!!---" }
    runApplication<BlogServiceApplication>(*args)
}