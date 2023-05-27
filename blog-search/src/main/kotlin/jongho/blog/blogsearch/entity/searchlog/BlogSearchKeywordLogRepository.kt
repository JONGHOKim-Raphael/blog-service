package jongho.blog.blogsearch.entity.searchlog

import org.springframework.data.jpa.repository.JpaRepository

interface BlogSearchKeywordLogRepository
    : JpaRepository<BlogSearchKeywordLog, String>, BlogSearchKeywordLogCustomRepository
{
    fun findByKeyword(keyword: String): BlogSearchKeywordLog?
    fun findCountByKeyword(keyword: String): Int
    fun findTop10ByOrderByCountDesc(): List<BlogSearchKeywordLog>
}