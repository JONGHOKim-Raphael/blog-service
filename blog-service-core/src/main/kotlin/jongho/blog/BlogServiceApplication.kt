package jongho.blog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BlogServiceApplication

fun main(args: Array<String>) {
    println("---HELLO, BLOG-SERVICE !!!---")
    runApplication<BlogServiceApplication>(*args)
}