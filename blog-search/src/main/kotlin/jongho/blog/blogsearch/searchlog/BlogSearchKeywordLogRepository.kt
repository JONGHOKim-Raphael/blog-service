package jongho.blog.blogsearch.searchlog

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface BlogSearchKeywordLogRepository : JpaRepository<BlogSearchKeywordLog, String> {
    fun findByKeyword(keyword: String): BlogSearchKeywordLog?
}