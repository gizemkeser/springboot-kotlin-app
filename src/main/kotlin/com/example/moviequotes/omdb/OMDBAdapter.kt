package com.example.moviequotes.omdb

interface OMDBAdapter {
    suspend fun getMovie(title: String): OMDBMovie?
}