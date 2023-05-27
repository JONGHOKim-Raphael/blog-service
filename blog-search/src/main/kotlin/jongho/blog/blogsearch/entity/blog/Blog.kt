package jongho.blog.blogsearch.entity.blog

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "blog")
data class Blog(
    @Id @Column(name = "id") var id: Long?,
    @Column(name = "name") val name: String,
    @Column(name = "uri") var uri: String?
)