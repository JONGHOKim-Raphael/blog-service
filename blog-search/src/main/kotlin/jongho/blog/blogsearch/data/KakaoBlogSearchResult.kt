package jongho.blog.blogsearch.data

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoBlogSearchResult (
    private var _totalCount: Int,
    private var _pageableCount: Int,
    private var _isEnd: Boolean,
    private val _documents: MutableList<BlogSearchDocument> = mutableListOf<BlogSearchDocument>()

) {
    fun toBlogSearchResponse() : BlogSearchResult {
        return BlogSearchResult(totalCount = _totalCount, pageableCount = _pageableCount, isEnd = _isEnd, documents = ArrayList(_documents))
    }

    @JsonProperty("meta")
    fun parseMeta(meta: Map<String, Any>) {
        _totalCount = meta["total_count"] as Int
        _pageableCount = meta["pageable_count"] as Int
        _isEnd = meta["is_end"] as Boolean
    }

    @JsonProperty("documents")
    fun parseDocuments(documents: List<Map<String, String>>) {

        for (document in documents) {
            val doc = BlogSearchDocument(
                title = document["title"] ?: "",
                content = document["content"] ?: "",
                url = document["url"] ?: "",
                blogname = document["blogname"] ?: "",
                thumbnail = document["thumbnail"] ?: "",
                datetime = document["datetime"] ?: ""
            )

            _documents.add(doc)
        }
    }
}