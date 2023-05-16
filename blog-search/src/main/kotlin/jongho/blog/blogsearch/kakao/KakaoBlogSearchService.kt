package jongho.blog.blogsearch.kakao

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jongho.blog.blogsearch.BlogSearchApi
import jongho.blog.blogsearch.BlogSortMethod

import io.netty.handler.logging.LogLevel
import jongho.blog.blogsearch.data.BlogSearchResult
import jongho.blog.blogsearch.data.KakaoBlogSearchResult
import jongho.blog.blogsearch.data.emptyBlogSearchResult
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ClientHttpConnector
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.transport.logging.AdvancedByteBufFormat

private val kakaoBlogSearchUri = "https://dapi.kakao.com/v2/search/blog"
private val kakaoRestApiKey = "KakaoAK 429f206db1aa4df9c85cae6171fc5423" // TODO: Encrypt this key, read the encrypted key, and use the decrypted key
private val httpClientConnector: ClientHttpConnector   // HTTP 통신들을 logging 하기 위함.
        = ReactorClientHttpConnector(HttpClient.create().wiretap(KakaoBlogSearchService::class.java.canonicalName, LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL))
private val defaultKakaoClient: WebClient = WebClient.builder()
    .clientConnector(httpClientConnector)
    .baseUrl(kakaoBlogSearchUri)
    .defaultHeader(HttpHeaders.AUTHORIZATION, kakaoRestApiKey)
    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    .build()
private val log: Logger = LoggerFactory.getLogger(KakaoBlogSearchService::class.java)

class KakaoBlogSearchService(
    private var _kakaoClient: WebClient = defaultKakaoClient
) : BlogSearchApi {

    fun KakaoBlogSearchService(kakaoClient: WebClient) {
        _kakaoClient = kakaoClient
    }

    override fun searchBlogsByKeyword(keyword: String, sort: BlogSortMethod, page: Int, size: Int): BlogSearchResult {
        // Data for form request
        val formRequest: MultiValueMap<String, String> = LinkedMultiValueMap<String, String>()
        formRequest.add("query", keyword)
        formRequest.add("sort", sort.toString())
        formRequest.add("page", page.toString())
        formRequest.add("size", size.toString())

        try {
            val json = _kakaoClient
                .method(HttpMethod.GET)
                .body(BodyInserters.fromFormData(formRequest))
                .exchangeToMono { response ->
                    when (response.statusCode()) {
                        HttpStatus.OK -> response.bodyToMono(String::class.java)
                        else -> {
                            throw KakaoBlogServerException(response.statusCode())
                        }
                    }
                }
                .share()
                .block()

            return json?.let { jacksonObjectMapper().readValue<KakaoBlogSearchResult>(json).toBlogSearchResponse() } ?: emptyBlogSearchResult
        }
        catch (ex: KakaoBlogServerException) {
            // 카카오 블로그 검색 서버에 장애가 있거나 요청에 문제가 있었던 경우,
            // 우선 empty response 를 return 하고 대안을 수행한다.
            log.error(ex.message)
            return emptyBlogSearchResult
        }
    }
}