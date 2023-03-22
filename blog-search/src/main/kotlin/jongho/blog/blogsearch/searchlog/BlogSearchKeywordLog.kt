package jongho.blog.blogsearch.searchlog

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "keyword_log")
data class BlogSearchKeywordLog (
    @Id @Column(name = "keyword") val keyword: String,
    @Column(name = "search_count", nullable = false) val count: Int
)