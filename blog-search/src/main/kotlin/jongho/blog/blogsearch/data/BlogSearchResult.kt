package jongho.blog.blogsearch.data

val emptyBlogSearchResult = BlogSearchResult(totalCount = 0, pageableCount = 0, isEnd = true, documents = listOf())
data class BlogSearchResult (
    val totalCount: Int,
    val pageableCount: Int,
    val isEnd: Boolean,
    val documents: List<BlogSearchDocument>
)