package com.example.moviequotes.utils

import com.example.moviequotes.movie.Movie
import com.example.moviequotes.omdb.OMDBMovie
import com.example.moviequotes.quote.Quote

fun mockMovie(): Movie {
    return Movie(1, "title1", "1990")
}

fun mockMovieList(): List<Movie> {
    return listOf(
        Movie(1, "title1", "1990"),
        Movie(2, "title2", "1990"))
}

fun mockOMDBMovie(): OMDBMovie{
    return OMDBMovie("title", "year", "poster-url")
}

fun mockQuote(): Quote {
    return Quote(1, "text", Movie(1, "title1", "1990"))
}

fun mockQuoteList(): List<Quote> {
    return listOf(
        Quote(1, "text", Movie(1, "title1", "1990")),
        Quote(1, "text", Movie(1, "title1", "1990")))
}