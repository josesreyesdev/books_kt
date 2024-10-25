package com.jsrdev.books.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class BookData(
    @JsonProperty("id") val id: Int = 0,
    @JsonProperty("title") val title: String = "",
    @JsonProperty("subjects") val subjects: List<String> = emptyList(),
    @JsonProperty("authors") val authors: List<AuthorData> = emptyList(),
    @JsonProperty("translators") val translators: List<AuthorData> = emptyList(),
    @JsonProperty("bookshelves") val bookshelves: List<String> = emptyList(),
    @JsonProperty("languages") val languages: List<String> = emptyList(),
    @JsonProperty("copyright") val copyright: Boolean? = null,
    @JsonProperty("media_type") val mediaType: String = "",
    @JsonProperty("formats") val formats: Map<String, String> = emptyMap(),
    @JsonProperty("download_count") val downloadCount: Int = 0
)
