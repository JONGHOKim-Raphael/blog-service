package jongho.blog.blogsearch

import jongho.blog.blogsearch.data.BlogSearchResult

val defaultSortBy = BlogSearchApiSortBy.ACCURACY
const val defaultSortByToString = "ACCURACY"
const val defaultPage = 1
const val defaultSize = 50

interface BlogSearchApi {
    // If there is no result, returns empty object with totalCount=0. Never returns null
    fun searchBlogsByKeyword(query: String, sort: BlogSearchApiSortBy = defaultSortBy, page: Int = defaultPage, size: Int = defaultSize): BlogSearchResult
}

enum class BlogSearchApiSortBy (
    val sortBy: String
) {
    ACCURACY("accuracy"),
    RECENCY("recency");

    override fun toString(): String {
        return sortBy
    }
}