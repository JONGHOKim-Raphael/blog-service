Using QueryDSL
======

## Query DSL Configuration

Add to `build.gradle.kts`:
```groovy
plugins {
    ...
    kotlin("kapt") version "1.8.20-RC"
}

subprojects {
    ...
    apply(plugin = "kotlin-kapt")
}

dependencies {
    ...
    
    // QueryDSL
    val querydslVersion = "5.0.0"
    implementation("com.querydsl:querydsl-jpa:${querydslVersion}:jakarta")
    kapt("com.querydsl:querydsl-apt:${querydslVersion}:jakarta")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
}
```

Add a configuration file:
```kotlin
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
```

## Custom Repository & Custom Repository Implementation
```kotlin
interface BlogSearchKeywordLogRepository
    : JpaRepository<BlogSearchKeywordLog, String>, BlogSearchKeywordLogCustomRepository
```

```kotlin
interface BlogSearchKeywordLogCustomRepository {
    // Your custom interface
}
```

```kotlin
class BlogSearchKeywordLogCustomRepositoryImpl (
    @Autowired val jpaQueryFactory: JPAQueryFactory
) : BlogSearchKeywordLogCustomRepository
```

## Implementing Custom Repository with Query DSL
Run `gradle build` and then you should import Qclass:
```kotlin
import jongho.blog.blogsearch.searchlog.QBlogSearchKeywordLog
```

```kotlin
import jongho.blog.blogsearch.searchlog.QBlogSearchKeywordLog
import com.querydsl.jpa.impl.JPAQueryFactory
...

class BlogSearchKeywordLogCustomRepositoryImpl (
    val qKeywordLog: QBlogSearchKeywordLog = QBlogSearchKeywordLog.blogSearchKeywordLog,
    @Autowired val jpaQueryFactory: JPAQueryFactory,
) : BlogSearchKeywordLogCustomRepository
```

## Using update/delete query with Query DSL
> **CAUTION**   
> The `@Transactional` annotation you should use is `org.springframework.transaction.annotation.Transactional`.    
> Do not use `javax.persistence.Transactional` or `jakarta.persistence.Transactional`

```kotlin
import org.springframework.transaction.annotation.Transactional
import jongho.blog.blogsearch.searchlog.QBlogSearchKeywordLog
import com.querydsl.jpa.impl.JPAQueryFactory
...

@Transactional
class BlogSearchKeywordLogCustomRepositoryImpl (
    val qKeywordLog: QBlogSearchKeywordLog = QBlogSearchKeywordLog.blogSearchKeywordLog,
    @Autowired val jpaQueryFactory: JPAQueryFactory,
) : BlogSearchKeywordLogCustomRepository
```

An example of insert statement:
```kotlin
jpaQueryFactory
    .insert(qKeywordLog)
    .columns(qKeywordLog.keyword, qKeywordLog.count)
    .values(keyword, 1)
    .execute()
```

An example of update statement:
```kotlin
jpaQueryFactory
    .update(qKeywordLog)
    .where(qKeywordLog.keyword.eq(keyword))
    .set(qKeywordLog.count, count+1)
    .execute()
```