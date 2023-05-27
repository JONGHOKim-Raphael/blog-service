package jongho.blog.blogsearch.data

data class BlogSearchDocument (
    val title: String,
    val content: String,
    val url: String,   // URI of blog posting
    val blogname: String,
    val thumbnail: String,   // URI of thumbnail image
    val datetime: String   // Format [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
    //    val datetime: OffsetDateTime
)