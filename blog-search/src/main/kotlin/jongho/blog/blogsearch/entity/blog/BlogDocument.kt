package jongho.blog.blogsearch.entity.blog

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "blog_document")
data class BlogDocument(
    @Id @Column(name = "id") var id : Long?,
    @Column(name = "title") val title: String,
    @Column(name = "content") val content: String,
    @Column(name = "uri") val uri: String,   // URI of blog posting
    @Column(name = "blog_id") val blogId: Long,
    @Column(name = "thumbnail_uri") val thumbnailUri: String,   // URI of thumbnail image
    @Column(name = "datetime") val datetime: String   // Format [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
)