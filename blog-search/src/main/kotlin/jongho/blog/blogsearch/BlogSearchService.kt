package jongho.blog.blogsearch

import jongho.blog.blogsearch.dto.BlogSearchResult
import jongho.blog.blogsearch.dto.BlogSortMethod
import jongho.blog.blogsearch.exception.HttpInternalServerErrorException
import jongho.blog.blogsearch.kakao.KakaoBlogSearchRequest
import jongho.blog.blogsearch.searchlog.BlogSearchKeywordLogRepository
import org.springframework.stereotype.Service

@Service
class BlogSearchService (
    val blogSearchKeywordLog: BlogSearchKeywordLogRepository,
    val kakao: KakaoBlogSearchRequest,
) {
    fun searchBlogsByKeyword(query: String, sort: BlogSortMethod, page: Int, size: Int) : BlogSearchResult {

        // keyword 검색된 횟수 증가 (+1)
        blogSearchKeywordLog.increaseCount(query)

        // TODO: "in-memory database 조회"

        // In-memory database 조회 실패 시, Kakao Blog Search API 조회
        try {
            return kakao.searchBlogsByKeyword(query, sort, page, size)
        }
        catch (ex: Exception) {
            Unit   // Kakao Blog Search Server 장애가 있을 경우 일단 대안 수행: Naver Blog Search API 조회
        }

        // Kakao Blog Search API 서버 장애 시, Naver Blog Search API 조회
        try {
            TODO("Naver Blog Search API 조회")
            //            return NaverBlogSearchService().searchBlogsByKeyword(keyword, sort, page, size)
        }
        catch (ex: Exception) {
            // HTTP 500 Internal Server Error 발생시켜야 함.
            // In-memory database, 카카오 블로그 검색 API, 네이버 블로그 검색 API 모두 장애가 있는 경우임
            throw HttpInternalServerErrorException("서버에 장애가 있습니다. 잠시 기다려주세요.")
        }
    }
}