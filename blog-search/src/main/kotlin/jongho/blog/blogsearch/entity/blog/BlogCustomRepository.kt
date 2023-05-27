package jongho.blog.blogsearch.entity.blog

import jongho.blog.blogsearch.dto.BlogSearchDocument
import jongho.blog.blogsearch.dto.BlogSearchResult

interface BlogCustomRepository {
    fun insert(blogSearchResult: BlogSearchResult) : Long
    fun insert(blogDocs: List<BlogSearchDocument>) : Long
}