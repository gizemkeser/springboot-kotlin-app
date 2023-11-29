package com.example.moviequotes.omdb

import com.fasterxml.jackson.annotation.JsonAlias

data class OMDBMovie(
    @JsonAlias("Title")
    val title: String?,
    @JsonAlias("Year")
    val year: String?,
    @JsonAlias("Poster")
    val poster: String?
)