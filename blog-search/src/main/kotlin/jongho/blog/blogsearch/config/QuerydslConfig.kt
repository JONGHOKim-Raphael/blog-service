// If you are curious about @PersistenceContext,
// See: https://stackoverflow.com/questions/31335211/autowired-vs-persistencecontext-for-entitymanager-bean/58891587#58891587
package jongho.blog.blogsearch.config

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext

@Configuration
class QuerydslConfig
{
    @PersistenceContext
    lateinit var entityManager: EntityManager

    @Bean
    fun jpaQueryFactory(): JPAQueryFactory {
        return JPAQueryFactory(entityManager)
    }
}