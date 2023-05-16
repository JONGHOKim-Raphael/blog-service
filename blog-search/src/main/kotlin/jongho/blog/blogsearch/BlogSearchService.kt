package jongho.blog.blogsearch

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jongho.blog.blogsearch.data.BlogSearchResult
import jongho.blog.blogsearch.data.emptyBlogSearchResult
import jongho.blog.blogsearch.exception.HttpBadRequestException
import jongho.blog.blogsearch.exception.HttpInternalServerErrorException
import jongho.blog.blogsearch.kakao.KakaoBlogSearchService

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

private val log : Logger = LoggerFactory.getLogger(BlogSearchService::class.java)

@Tag(name = "블로그 검색")
@RestController
@RequestMapping("v1/blog")
class BlogSearchService : BlogSearchApi {

    @Operation(summary = "블로그 검색", description = "키워드를 이용해 블로그를 검색합니다.")
    @GetMapping("/search")
    fun showBlogsByKeyword(
        @Parameter(name = "검색할 키워드") @RequestParam("query", required = true) query: String,
        @Parameter(name = "검색결과 정렬방식") @RequestParam("pageSortMethod", required = false, defaultValue = defaultPageSortMethodToString) pageSortMethod: BlogSearchApiSortBy,
        @Parameter(name = "검색결과 페이지 번호") @RequestParam("pageNumber", required = false, defaultValue = defaultPageNumber.toString()) pageNumber: Int,
        @Parameter(name = "검색결과 페이지당 결과개수") @RequestParam("pageSize", required = false, defaultValue = defaultPageSize.toString()) pageSize: Int) : ResponseEntity<String> {

        try {
            if (pageSize < 1 || pageSize > 50) {
                throw HttpBadRequestException("Parameter *size* should be integer and in the range of 1-50")
            }

            if (pageNumber < 1 || pageNumber > 50) {
                throw HttpBadRequestException("Parameter *page* should be integer and in the range of 1-50")
            }
        }
        catch (ex: HttpBadRequestException) {
            // User 에게 HTTP 400 BAD REQUEST 로 응답해주고, size 가 잘못되어 있는지 page 가 잘못되어 있는지 알려준다.
            return ResponseEntity.badRequest().body(ex.message)
        }

        try {
            val result = searchBlogsByKeyword(query, pageSortMethod, pageNumber, pageSize)

            if (result == emptyBlogSearchResult)
                return ResponseEntity.ok("검색어에 해당하는 결과가 없습니다.")

            return ResponseEntity.ok(result.documents.toString())
        }
        catch (ex: HttpInternalServerErrorException) {
            return ResponseEntity.internalServerError().body(ex.message)
        }
    }

    override fun searchBlogsByKeyword(query: String, sort: BlogSearchApiSortBy, page: Int, size: Int) : BlogSearchResult {

        // TODO: keyword 검색 기록 저장

        // TODO: "in-memory database 조회"

        // In-memory database 조회 실패 시, Kakao Blog Search API 조회
        try {
            return KakaoBlogSearchService().searchBlogsByKeyword(query, sort, page, size)
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