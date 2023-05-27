package jongho.blog.blogsearch.entity.blog

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface BlogDocumentRepository : JpaRepository<BlogDocument, Long>, BlogCustomRepository {
    fun findBlogDocumentsByContentContaining(keyword: String, pageable: Pageable) : List<BlogDocument>
}