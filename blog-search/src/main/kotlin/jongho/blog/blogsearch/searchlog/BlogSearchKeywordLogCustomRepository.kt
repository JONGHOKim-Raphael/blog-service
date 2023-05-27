package jongho.blog.blogsearch.searchlog

interface BlogSearchKeywordLogCustomRepository {
    fun increaseCount(keyword: String) : Unit
    fun findAndIncreaseCount(keyword: String) : Int
}