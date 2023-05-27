package jongho.blog.blogsearch.entity.blog

import com.querydsl.jpa.impl.JPAQueryFactory
import jongho.blog.blogsearch.dto.BlogSearchDocument
import jongho.blog.blogsearch.dto.BlogSearchResult
import org.springframework.beans.factory.annotation.Autowired

// QueryDSL Q-Classes
import jongho.blog.blogsearch.entity.blog.QBlog
import jongho.blog.blogsearch.entity.blog.QBlogDocument
import org.springframework.transaction.annotation.Transactional

@Transactional
class BlogCustomRepositoryImpl (
    @Autowired val jpaQueryFactory: JPAQueryFactory,
    val qBlog : QBlog = QBlog.blog,
    val qDoc : QBlogDocument = QBlogDocument.blogDocument
) : BlogCustomRepository {

    override fun insert(blogSearchResult: BlogSearchResult): Long {
        return insert(blogSearchResult.documents)
    }

    override fun insert(blogDocs: List<BlogSearchDocument>): Long {

        var count : Long = 0

        for (doc in blogDocs) {
            val exist = jpaQueryFactory
                .selectFrom(qDoc)
                .where(qDoc.uri.eq(doc.url))
                .fetchOne() != null

            if (!exist) {

                var blogId = jpaQueryFactory
                    .select(qBlog.id)
                    .from(qBlog)
                    .where(qBlog.name.eq(doc.blogname))
                    .fetchOne()

                if (blogId == null) {
                    // insert blog and get id
                    jpaQueryFactory
                        .insert(qBlog)
                        .columns(qBlog.name)
                        .values(doc.blogname)
                        .execute()

                    blogId = jpaQueryFactory
                        .select(qBlog.id)
                        .from(qBlog)
                        .where(qBlog.name.eq(doc.blogname))
                        .fetchOne()
                }

                jpaQueryFactory
                    .insert(qDoc)
                    .columns(qDoc.uri, qDoc.title, qDoc.content, qDoc.thumbnailUri, qDoc.datetime, qDoc.blogId)
                    .values(doc.url, doc.title, doc.content, doc.thumbnail, doc.datetime, blogId)
                    .execute()

                count++
            }
        }

        return count
    }
}