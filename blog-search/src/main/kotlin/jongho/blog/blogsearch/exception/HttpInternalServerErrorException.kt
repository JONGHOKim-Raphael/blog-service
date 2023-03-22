package jongho.blog.blogsearch.exception

import org.springframework.http.HttpStatus

class HttpInternalServerErrorException(message: String) : Exception(message) {
    fun httpStatusCode() : HttpStatus { return HttpStatus.INTERNAL_SERVER_ERROR }
}