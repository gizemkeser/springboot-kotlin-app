package com.example.moviequotes.movie

import com.example.moviequotes.omdb.OMDBMovie

interface MovieService {
    fun findAll(): List<Movie>
    fun findById(id: Long): Movie?
    fun save(movie: Movie): Movie
    fun delete(id: Long): Boolean
    suspend fun search(title: String): OMDBMovie?
}