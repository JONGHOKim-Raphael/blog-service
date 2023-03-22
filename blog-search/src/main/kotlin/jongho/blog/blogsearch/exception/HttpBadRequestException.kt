package jongho.blog.blogsearch.exception

import org.springframework.http.HttpStatus

class HttpBadRequestException(message: String): Exception(message) {
    fun httpStatusCode() : HttpStatus { return HttpStatus.BAD_REQUEST }
}