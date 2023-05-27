// This code should work with jakarta.* packages. See:
//  - http://querydsl.com/static/querydsl/5.0.0/reference/html/ch02s03.html
//  - https://github.com/querydsl/querydsl/issues/3027
//  - https://docs.spring.io/spring-boot/docs/2.7.9/reference/html/dependency-versions.html
//  - https://stackoverflow.com/questions/73257636/using-hibernate-6-x-with-spring-boot-2-7-x-not-working
package jongho.blog.blogsearch.entity.searchlog

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

// QueryDSL Q-Class
import jongho.blog.blogsearch.entity.searchlog.QBlogSearchKeywordLog

@Transactional
class BlogSearchKeywordLogCustomRepositoryImpl (
    val qKeywordLog: QBlogSearchKeywordLog = QBlogSearchKeywordLog.blogSearchKeywordLog,
    @Autowired val jpaQueryFactory: JPAQueryFactory
) : BlogSearchKeywordLogCustomRepository
{
    override fun increaseCount(keyword: String): Unit {
        val result = jpaQueryFactory
            .selectFrom(qKeywordLog)
            .where(qKeywordLog.keyword.eq(keyword))
            .fetchOne()

        if (result == null) {
            println("Hello, result == null")
            jpaQueryFactory
                .insert(qKeywordLog)
                .columns(qKeywordLog.keyword, qKeywordLog.count)
                .values(keyword, 1)
                .execute()
        }
        else {   // result != null
            val count = result.count

            jpaQueryFactory
                .update(qKeywordLog)
                .where(qKeywordLog.keyword.eq(keyword))
                .set(qKeywordLog.count, count+1)
                .execute()
        }
    }

    override fun findAndIncreaseCount(keyword: String): Int {

        val result = jpaQueryFactory
            .selectFrom(qKeywordLog)
            .where(qKeywordLog.keyword.eq(keyword))
            .fetchOne()

        if (result == null) {
            jpaQueryFactory
                .insert(qKeywordLog)
                .columns(qKeywordLog.keyword, qKeywordLog.count)
                .values(keyword, 1)
                .execute()

            return 0
        }
        else {   // result != null
            val count = result.count

            jpaQueryFactory
                .update(qKeywordLog)
                .where(qKeywordLog.keyword.eq(keyword))
                .set(qKeywordLog.count, count+1)
                .execute()

            return count
        }
    }
}