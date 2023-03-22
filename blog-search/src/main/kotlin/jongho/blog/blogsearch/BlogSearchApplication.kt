package jongho.blog.blogsearch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BlogSearchApplication

fun main(args: Array<String>) {
    println("--- Hello, BLOG-SEARCH !!! ---")
    runApplication<BlogSearchApplication>(*args)
}