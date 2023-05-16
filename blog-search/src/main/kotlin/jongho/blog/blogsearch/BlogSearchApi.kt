package jongho.blog.blogsearch

import jongho.blog.blogsearch.data.BlogSearchResult

val defaultSortMethod = BlogSearchApiSortBy.ACCURACY
const val defaultPageSortMethodToString = "ACCURACY"
const val defaultPageNumber = 1
const val defaultPageSize = 50

interface BlogSearchApi {
    // If there is no result, returns empty object with totalCount=0. Never returns null
    fun searchBlogsByKeyword(query: String, sort: BlogSearchApiSortBy = defaultSortMethod, page: Int = defaultPageNumber, size: Int = defaultPageSize): BlogSearchResult
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