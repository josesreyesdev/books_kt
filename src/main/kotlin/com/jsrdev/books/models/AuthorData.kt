package com.jsrdev.books.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class AuthorData (
    @JsonProperty("name") val name: String = "",
    @JsonProperty("birth_year") val birthYear: Int = 0,
    @JsonProperty("dead_year") val deadYear: Int? = null
)
