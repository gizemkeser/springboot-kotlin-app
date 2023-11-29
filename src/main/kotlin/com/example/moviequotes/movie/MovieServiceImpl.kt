package com.example.moviequotes.movie

import com.example.moviequotes.omdb.OMDBAdapter
import com.example.moviequotes.omdb.OMDBMovie
import org.springframework.stereotype.Service

@Service
class MovieServiceImpl(
    private val movieRepository: MovieRepository,
    private val omdbAdapter: OMDBAdapter
) : MovieService {
    override fun findAll(): List<Movie> = movieRepository.findAll().toList()
    override fun findById(id: Long): Movie? = movieRepository.findById(id).orElse(null)
    override fun save(movie: Movie): Movie = movieRepository.save(movie)
    override fun delete(id: Long): Boolean =
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id)
            true
        } else false
    override suspend fun search(title: String): OMDBMovie? = omdbAdapter.getMovie(title)
}
