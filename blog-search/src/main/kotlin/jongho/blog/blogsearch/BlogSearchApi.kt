package jongho.blog.blogsearch

import jongho.blog.blogsearch.data.BlogSearchResult

val defaultBlogSortMethod = BlogSortMethod.ACCURACY
const val defaultBlogSortMethodToString = "ACCURACY"
const val defaultPageNumber = 1
const val defaultPageSize = 50

interface BlogSearchApi {
    // If there is no result, returns empty object with totalCount=0. Never returns null
    fun searchBlogsByKeyword(query: String, sort: BlogSortMethod = defaultBlogSortMethod, page: Int = defaultPageNumber, size: Int = defaultPageSize): BlogSearchResult
}

enum class BlogSortMethod (
    val sortBy: String
) {
    ACCURACY("accuracy"),
    RECENCY("recency");

    override fun toString(): String {
        return sortBy
    }
}