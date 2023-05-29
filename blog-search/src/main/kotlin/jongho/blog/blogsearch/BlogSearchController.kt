/**
 *   Implementation of a REST API service.
 *
 *   NOTE: Open API v3's `@Parameter` is buggy, so we do not use it.
 */
package jongho.blog.blogsearch

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jongho.blog.blogsearch.dto.BlogSortMethod
import jongho.blog.blogsearch.dto.emptyBlogSearchResult
import jongho.blog.blogsearch.exception.HttpBadRequestException
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

private val log : Logger = LoggerFactory.getLogger(BlogSearchController::class.java)

@ComponentScan("jongho.blog.blogsearch")
@Tag(name = "블로그 검색")
@RestController
@RequestMapping("v1/blog")
class BlogSearchController  (
    val service: BlogSearchService,
) {
    @Operation(summary = "블로그 검색", description = "키워드를 이용해 블로그를 검색합니다.")
    @GetMapping("/search")
    fun showBlogsByKeyword(
        @RequestParam("query", required = true) query: String,
        @RequestParam("blogSortMethod", required = false, defaultValue = defaultBlogSortMethodToString) blogSortMethod: BlogSortMethod,
        @RequestParam("pageNumber", required = false, defaultValue = defaultPageNumber.toString()) pageNumber: Int,
        @RequestParam("pageSize", required = false, defaultValue = defaultPageSize.toString()) pageSize: Int) : ResponseEntity<String> {

        if (pageSize < 1 || pageSize > 50) {
            throw HttpBadRequestException("Parameter *size* should be integer and in the range of 1-50")
        }

        if (pageNumber < 1 || pageNumber > 50) {
            throw HttpBadRequestException("Parameter *page* should be integer and in the range of 1-50")
        }

        val result = service.searchBlogsByKeyword(query, blogSortMethod, pageNumber, pageSize)

        return if (result == emptyBlogSearchResult)
            ResponseEntity.ok("검색어에 해당하는 결과가 없습니다.")
        else
            ResponseEntity.ok(result.documents.toString())
    }

    @Operation(summary = "블로그 검색", description = "키워드를 이용해 블로그를 검색합니다. 첫 페이지 결과를 반환합니다.")
    @GetMapping("/search/keyword")
    fun showBlogsByKeyword(@RequestParam("query", required = true) query: String) : ResponseEntity<String> {
        return showBlogsByKeyword(query, defaultBlogSortMethod, defaultPageNumber, defaultPageSize)
    }

    @Operation(summary = "블로그 검색", description = "키워드를 이용해 블로그를 검색합니다. 첫 페이지 결과를 반환합니다.")
    @GetMapping("/search/first-page/keyword")
    fun showBlogsFirstPageByKeyword(@RequestParam("query", required = true) query: String,
                                    @RequestParam("pageSize", required = false, defaultValue = defaultPageSize.toString()) pageSize: Int) : ResponseEntity<String> {
        return showBlogsByKeyword(query, defaultBlogSortMethod, defaultPageNumber, pageSize)
    }
}