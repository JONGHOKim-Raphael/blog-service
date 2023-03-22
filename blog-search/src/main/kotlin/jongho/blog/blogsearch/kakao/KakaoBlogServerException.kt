package jongho.blog.blogsearch.kakao

import org.springframework.http.HttpStatus

class KakaoBlogServerException (val httpStatusCode: HttpStatus)
    : Exception("HTTP ${httpStatusCode} ${httpStatusCode.toString()} by kakao blog search server")