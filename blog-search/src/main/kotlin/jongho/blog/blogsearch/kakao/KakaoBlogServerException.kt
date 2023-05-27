package jongho.blog.blogsearch.kakao

import org.springframework.http.HttpStatusCode

class KakaoBlogServerException(val httpStatusCode: HttpStatusCode)
    : Exception("HTTP ${httpStatusCode} ${httpStatusCode.toString()} by kakao blog search server")