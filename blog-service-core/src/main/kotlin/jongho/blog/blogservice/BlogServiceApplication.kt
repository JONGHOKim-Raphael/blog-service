package jongho.blog.blogservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = [
        "jongho.blog.blogservice",
        "jongho.blog.blogsearch"
    ]
)
class BlogServiceApplication

fun main(args: Array<String>) {
    println("---HELLO, BLOG-SERVICE !!!---")
    runApplication<BlogServiceApplication>(*args)
}