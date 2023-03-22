package jongho.blog.blogservice.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@ComponentScan(basePackages = [
    "jongho.blog.blogservice",
    "jongho.blog.blogsearch"
])
@Configuration
class OpenApiConfig {
    @Bean
    fun openApi(@Value("v1.0.0") springdocVersion: String): OpenAPI {
        val info = Info()
            .title("JONGHO KIM's Blog Service")
            .version(springdocVersion)
            .description("김종호의 블로그 서비스: 키워드로 블로그 검색, Top-10 인기 키워드 확인")

        return OpenAPI()
            .components(Components())
            .info(info)
    }
}