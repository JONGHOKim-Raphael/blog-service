/**
 *   Definition of enum type used for request.
 *   We use annotation `@RequestParam` (`org.springframework.web.bind.annotation.RequestParam`) to serve REST API.
 *   If you use primitive type parameter, the `@RequestParam` will automatically convert string value to your primitive type.
 *   Else, you should implement converter using `org.springframework.core.convert.converter.Converter`.
 */
package jongho.blog.blogsearch.data

import jongho.blog.blogsearch.defaultBlogSortMethod
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter

enum class BlogSortMethod (
    val sortBy: String
) {
    ACCURACY("accuracy"),
    RECENCY("recency");

    override fun toString(): String {
        return sortBy
    }
}

@Configuration
public class BlogSortMethodConverter : Converter<String, BlogSortMethod> {
    override fun convert(source: String): BlogSortMethod? {
        return when(source) {
            BlogSortMethod.ACCURACY.toString() -> BlogSortMethod.ACCURACY
            BlogSortMethod.RECENCY.toString()  -> BlogSortMethod.RECENCY
            else -> defaultBlogSortMethod   // NOTE: You do not have to specify the value of optional parameter `blogSortMethod
        }
    }
}