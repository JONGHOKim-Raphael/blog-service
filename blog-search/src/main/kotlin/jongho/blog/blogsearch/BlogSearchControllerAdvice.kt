package jongho.blog.blogsearch

import jongho.blog.blogsearch.exception.HttpBadRequestException
import jongho.blog.blogsearch.exception.HttpInternalServerErrorException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class BlogSearchControllerAdvice {
    @ExceptionHandler(HttpBadRequestException::class)
    fun handleInternalServerError(ex: HttpBadRequestException) : ResponseEntity<String> {
        return ResponseEntity.badRequest().body(ex.message)
    }

    @ExceptionHandler(HttpInternalServerErrorException::class)
    fun handleInternalServerError(ex: HttpInternalServerErrorException) : ResponseEntity<String> {
        return ResponseEntity.internalServerError().body(ex.message)
    }
}