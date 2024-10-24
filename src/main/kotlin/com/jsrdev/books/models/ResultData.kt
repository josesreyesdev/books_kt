package com.jsrdev.books.models

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class ResultData(
    @JsonAlias("count") val count: Int = 0,
    @JsonAlias("next") val next: String? = null,
    @JsonAlias("previous") val previous: String? = null,
    @JsonAlias("results") val results: List<BookData> = emptyList()
)
