package jongho.blog.blogsearch

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jongho.blog.blogsearch.searchlog.BlogSearchKeywordLogRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.text.StringBuilder

@Tag(name = "블로그 검색")
@RestController
@RequestMapping("v1/blog")
class BlogTop10Controller (
    @Autowired val keywordLog: BlogSearchKeywordLogRepository
)
{
    @Operation(summary = "블로그 검색 Top-10 키워드", description = "블로그를 검색하기 위해 사용된 키워드 중 상위 10개 및 검색횟수를 보여줍니다.")
    @GetMapping("/top-10-keywords")
    fun top10Keyword() : String {
        val top10 = keywordLog.findTop10ByOrderByCountDesc()
        var builder: StringBuilder = StringBuilder("")

        top10.forEachIndexed { idx, keywordLog ->
            builder.append("${idx+1}, ${keywordLog.keyword}, ${keywordLog.count}회")
            if (idx < 9)
                builder.append("\n")
        }

        return builder.toString()
    }
}