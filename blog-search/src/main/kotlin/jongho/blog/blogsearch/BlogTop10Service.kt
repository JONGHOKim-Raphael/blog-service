package jongho.blog.blogsearch

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "블로그 검색")
@RestController
@RequestMapping("v1/blog")
class BlogTop10Service {
    @Operation(summary = "블로그 검색 Top-10 키워드", description = "블로그를 검색하기 위해 사용된 키워드 중 상위 10개 및 검색횟수를 보여줍니다.")
    @GetMapping("/top-10-keywords")
    fun top10Keyword() : List<String> {
        return listOf("1st 집짓기 1000", "2nd 집부수기 700", "3rd 내 집 마련 49")
    }
}