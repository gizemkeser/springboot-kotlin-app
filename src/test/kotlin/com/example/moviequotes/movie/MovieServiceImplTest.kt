package com.example.moviequotes.movie

import com.example.moviequotes.omdb.OMDBAdapter
import com.example.moviequotes.omdb.OMDBAdapterImpl
import com.example.moviequotes.omdb.OMDBWebClient
import com.example.moviequotes.utils.mockMovie
import com.example.moviequotes.utils.mockMovieList
import com.example.moviequotes.utils.mockOMDBMovie
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import java.util.*

@ExtendWith(MockitoExtension::class)
class MovieServiceImplTest {
    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var omdbWebClient: OMDBWebClient

    @Mock
    private lateinit var omdbAdapter: OMDBAdapter

    @InjectMocks
    private lateinit var omdbAdapterImpl: OMDBAdapterImpl

    @InjectMocks
    private lateinit var movieServiceImpl: MovieServiceImpl

    @Test
    fun `should add a movie when a movie is posted`() {
        val movie = mockMovie()
        whenever(movieRepository.save(movie)).thenReturn(movie)
        Assertions.assertEquals(movie, movieServiceImpl.save(movie))
    }

    @Test
    fun `should return all movies when all movies are requested`() {
        val movieList = mockMovieList()
        whenever(movieRepository.findAll()).thenReturn(movieList)
        Assertions.assertEquals(movieList, movieServiceImpl.findAll())
    }

    @Test
    fun `should return movie when the movie is find for the given id`() {
        val movie = Optional.of(mockMovie())
        whenever(movieRepository.findById(any())).thenReturn(movie)
        Assertions.assertEquals(movie.get(), movieServiceImpl.findById(1))
    }

    @Test
    fun `should return null when the movie is not find for the given id`() {
        whenever(movieRepository.findById(any())).thenReturn(Optional.empty())
        Assertions.assertEquals(null, movieServiceImpl.findById(1))
    }

    @Test
    fun `should return true when movie to be deleted is found`() {
        whenever(movieRepository.existsById(any())).thenReturn(true)
        Assertions.assertEquals(true, movieServiceImpl.delete(1))
    }

    @Test
    fun `should return false when movie to be deleted is not found`() {
        whenever(movieRepository.existsById(any())).thenReturn(false)
        Assertions.assertEquals(false, movieServiceImpl.delete(1))
    }

    @Test
    fun `should return a omdb movie when the search is successful`() {
        val omdbMovie = mockOMDBMovie()
        runBlocking {
            launch {
                whenever(omdbWebClient.getMovie(any())).thenReturn(omdbMovie)
                Assertions.assertEquals(omdbMovie, omdbAdapterImpl.getMovie("title"))
            }
        }
    }

}
