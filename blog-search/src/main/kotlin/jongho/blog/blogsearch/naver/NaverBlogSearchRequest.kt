package jongho.blog.blogsearch.naver

import jongho.blog.blogsearch.BlogSearchRequestApi
import jongho.blog.blogsearch.dto.BlogSearchResult
import jongho.blog.blogsearch.dto.BlogSortMethod

class NaverBlogSearchRequest : BlogSearchRequestApi {
    override fun searchBlogsByKeyword(query: String, sort: BlogSortMethod, page: Int, size: Int): BlogSearchResult {
        TODO("Not yet implemented")
    }
}