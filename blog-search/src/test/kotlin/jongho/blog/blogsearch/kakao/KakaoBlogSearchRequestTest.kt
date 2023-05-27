package jongho.blog.blogsearch.kakao

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.ints.shouldBeGreaterThanOrEqual
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldNotBe
import jongho.blog.blogsearch.dto.BlogSearchResult
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.reactive.function.client.WebClient

private const val kakaoBlogSearchUri = "https://dapi.kakao.com/v2/search/blog"
private const val kakaoRestApiKey = "KakaoAK 429f206db1aa4df9c85cae6171fc5423" // TODO: Encrypt this key, read the encrypted key, and use the decrypted key
private val mockKakaoClient: WebClient = WebClient.builder()
    .baseUrl(kakaoBlogSearchUri)
    .defaultHeader(HttpHeaders.AUTHORIZATION, kakaoRestApiKey)
    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    .build()
private val kakaoClientToWrongServer: WebClient = WebClient.builder()
    .baseUrl("$kakaoBlogSearchUri/wrong-and-not-existing-path/2afE52D0x#135087")
    .defaultHeader(HttpHeaders.AUTHORIZATION, kakaoRestApiKey)
    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    .build()

@ExtendWith(SpringExtension::class)
class KakaoBlogSearchRequestTest : BehaviorSpec({

    Given("실제 카카오 블로그 검색 서버에 요청했을 때 ") {
        val kakaoBlogSearchRequest = KakaoBlogSearchRequest()

        When("*집짓기* 라는 키워드로 블로그 검색했다면") {
            val query = "집짓기"
            val response : BlogSearchResult = kakaoBlogSearchRequest.searchBlogsByKeyword(query)

            Then("null 이 아닌 결과를 반환해야 함.") {
                response.shouldNotBeNull()   // assertion & smart-casting 한 번에 해결
                response.totalCount shouldNotBe null
                response.pageableCount shouldNotBe null
                response.isEnd shouldNotBe null
                response.documents shouldNotBe null
                response.totalCount shouldBeGreaterThanOrEqual response.documents.size
            }
        }
    }

    Given("존재하지 않을법한 카카오 서버에 요청했을 때") {
        val serviceToWrongServer = KakaoBlogSearchRequest(kakaoClientToWrongServer)

        When("똑같이 *집짓기* 라는 키워드로 검색 요청하면") {
            val query = "집짓기"

            Then("Exception 이 나와야 함 (주로 ReactiveException)") {
                shouldThrow<Exception> {
                    serviceToWrongServer.searchBlogsByKeyword(query)
                }
            }
        }
    }
})