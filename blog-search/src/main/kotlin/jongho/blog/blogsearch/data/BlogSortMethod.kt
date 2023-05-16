/**
 *   Definition of enum type used for request.
 *   If you use primitive type parameter, `@RequestParam` will automatically convert string value to your primitive type.
 *   Else, you should implement converter using `org.springframework.core.convert.converter.Converter`.
 */
package jongho.blog.blogsearch.data

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
            else -> null
        }
    }
}