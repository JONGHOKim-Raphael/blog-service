package jongho.blog.blogsearch.searchlog

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "keyword_log")
data class BlogSearchKeywordLog (
    @Id @Column(name = "keyword") val keyword: String,
    @Column(name = "count", nullable = false) var count: Int
)