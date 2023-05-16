package jongho.blog.blogsearch

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
class BlogSearchServiceTest : FunSpec({

    val service = BlogSearchService()

    test("잘못된 size 로 블로그 검색 요청할 때") {
        val query = "집짓기"
        val wrongPageSize = 60
        val response = service.showBlogsByKeyword(
            query = query, pageSize = wrongPageSize,
            blogSortMethod = defaultBlogSortMethod, pageNumber = defaultPageNumber,)

        response.statusCode shouldBe HttpStatus.BAD_REQUEST
        response.body shouldContain "size"   // Error message is: "Bad Request. Parameter *size* should be integer and in the range of 1-50"
    }

    test("잘못된 page 로 블로그 검색 요청할 때") {
        val query = "집짓기"
        val wrongPageNumber = -1
        val response = service.showBlogsByKeyword(
            query = query, pageNumber = wrongPageNumber,
            blogSortMethod = defaultBlogSortMethod, pageSize = defaultPageSize)

        response.statusCode shouldBe HttpStatus.BAD_REQUEST
        response.body shouldContain "page"   // Error message is: "Bad Request. Parameter *page* should be integer and in the range of 1-50"
    }
})