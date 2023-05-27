package jongho.blog.blogsearch

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.longs.shouldBeGreaterThanOrEqual
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import jongho.blog.blogsearch.searchlog.BlogSearchKeywordLogRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import java.util.*

@ActiveProfiles("test")
@DataJpaTest(showSql = true)
class BlogSearchKeywordLogRepositoryTest (
    @Value("\${spring.datasource.url}") val springDatasourceUrl: String,
    @Value("\${spring.datasource.driverClassName}") val springDatasourceDriverClassName: String,
    @Value("\${spring.jpa.database-platform}") val springJpaDatabasePlatform: String,
    @Value("\${spring.sql.init.mode}") val springSqlInitMode: String,
    @Value("\${spring.jpa.hibernate.ddl-auto}") val springJpaHibernateDdlAuto: String,
    @Autowired val keywordLog: BlogSearchKeywordLogRepository
) : FunSpec({
    // NOTE: test/resources/schema.sql 및 test/resources/data.sql 에 의해 초기화 됨.

    beforeSpec() {
        // main/resources/application-test.yml 에 정의된 설정값들 중
        // 테스트에서 덮어쓰지 않은 설정값들 확인
        springDatasourceUrl shouldBe "jdbc:h2:~/test"  // "jdbc:h2:mem:testdb"
        springDatasourceDriverClassName shouldBe "org.h2.Driver"
        springJpaDatabasePlatform shouldBe "org.hibernate.dialect.H2Dialect"

        // 테스트용 설정파일 test/resources/application-test.yml 에 정의된 설정값들이 잘 들어갔는지 확인
        springSqlInitMode shouldBe "always"
        springJpaHibernateDdlAuto shouldBe "none"

        // DB 잘 초기화됐는지 확인
        keywordLog.count() shouldBeGreaterThanOrEqual 0
    }

    context("영어 데이터 먼저 테스트") {
        val keyword1 = "house"

        test("영어 keyword 검색기록 존재여부 찾기") {
            val existResult1 = keywordLog.existsById(keyword1)

            existResult1 shouldBe true
        }
        test("영어 keyword 가 검색된 횟수 조회") {
            val findResult1 = keywordLog.findById(keyword1)

            findResult1.get().count shouldBe 700
        }
    }

    context("한글 데이터 테스트") {
        val keyword2 = "집짓기"
        val keyword3 = "집부수기"

        test("한글 keyword 검색기록 존재여부 찾기") {
            val existResult2 = keywordLog.existsById(keyword2)
            val existResult3 = keywordLog.existsById(keyword3)

            existResult2 shouldBe true
            existResult3 shouldBe true
        }
        test("한글 keyword 가 검색된 횟수 조회") {
            val findResult2 = keywordLog.findById(keyword2)
            val findResult3 = keywordLog.findById(keyword3)

            findResult2.get().count shouldBe 450
            findResult3.get().count shouldBe 268
        }
    }

    context("한글+특수문자 데이터 테스트") {
        val keyword4 = "★모든 집 다 때려부수고 다시 짓기☆"

        test("한글+특수문자 keyword 검색기록 존재여부 찾기") {
            val existResult4 = keywordLog.existsById(keyword4)
            existResult4 shouldBe false
        }

        test("검색기록이 존재하지 않는 한글+특수문자 keyword 가 검색된 횟수 조회") {
            val findResult4 = keywordLog.findById(keyword4)
            findResult4 shouldBe Optional.empty()
            shouldThrow<Exception> { findResult4.get() }
        }
    }

    context("repository에 명시한 함수 테스트") {
        val keyword1 = "house"
        val keyword2 = "집짓기"
        val keyword3 = "집부수기"

        test("findByKeyword() 테스트") {
            val findResult1 =keywordLog.findByKeyword(keyword1)

            findResult1.shouldNotBeNull()
            findResult1.count shouldBe 700
        }

        /*
        test("Top-N keyword 테스트: findTop10ByCountOrderByCountDesc()") {
            val top10Result = keywordLog.top10Keyword()

            top10Result.size shouldBeGreaterThanOrEqual 1
            top10Result[0].keyword shouldBe "house"
            top10Result[0].count shouldBe 700
            top10Result[1].keyword shouldBe "집짓기"
            top10Result[1].count shouldBe 450
            top10Result[2].keyword shouldBe "집부수기"
            top10Result[2].count shouldBe 268
        }
         */
    }
})