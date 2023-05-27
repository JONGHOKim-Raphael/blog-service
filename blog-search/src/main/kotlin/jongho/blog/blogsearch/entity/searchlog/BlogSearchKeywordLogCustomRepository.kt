package jongho.blog.blogsearch.entity.searchlog

interface BlogSearchKeywordLogCustomRepository {
    fun increaseCount(keyword: String) : Unit
    fun findAndIncreaseCount(keyword: String) : Int
}