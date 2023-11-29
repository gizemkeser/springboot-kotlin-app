package com.example.moviequotes.movie

import com.example.moviequotes.utils.mockMovie
import com.example.moviequotes.utils.mockMovieList
import com.example.moviequotes.utils.mockOMDBMovie
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@WebMvcTest(MovieController::class)
class MovieControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper
) {
    @MockBean
    private lateinit var movieService: MovieService

    @Test
    fun `should add a movie when a movie is posted`() {
        val movie = mockMovie()
        whenever(movieService.save(movie)).thenReturn(movie)

        mockMvc.post("/movie") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(movie)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isCreated() }
            content {
                contentType(MediaType.APPLICATION_JSON)
                json(objectMapper.writeValueAsString(movie))
            }
        }
    }

    @Test
    fun `should return 500 status code when a problem occurred while posting a movie`() {
        val movie = mockMovie()
        whenever(movieService.save(movie)).thenThrow(RuntimeException())

        mockMvc.post("/movie") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(movie)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isInternalServerError() }
        }
    }

    @Test
    fun `should return all movies when all movies are requested`() {
        val movieList = mockMovieList()
        whenever(movieService.findAll()).thenReturn(movieList)

        mockMvc.get("/movie").andExpect {
            status { isOk() }
            content {
                contentType(MediaType.APPLICATION_JSON)
                json(objectMapper.writeValueAsString(movieList))
            }
        }
    }

    @Test
    fun `should return 500 status code when a problem occurred while all movies are requested`() {
        whenever(movieService.findAll()).thenThrow(RuntimeException())

        mockMvc.get("/movie").andExpect {
            status { isInternalServerError() }
        }
    }


    @Test
    fun `should return a movie when movie is found for the given id`() {
        val movie = mockMovie()
        whenever(movieService.findById(any())).thenReturn(movie)

        mockMvc.get("/movie/{id}", 1).andExpect {
            status { isOk() }
            content {
                contentType(MediaType.APPLICATION_JSON)
                json(objectMapper.writeValueAsString(movie))
            }
        }
    }

    @Test
    fun `should return 404 status code when movie is not found for the given id`() {
        whenever(movieService.findById(any())).thenReturn(null)

        mockMvc.get("/movie/{id}", 1).andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun `should return 500 status code when error is occurred while finding movie`() {
        whenever(movieService.findById(any())).thenThrow(RuntimeException())

        mockMvc.get("/movie/{id}", 1).andExpect {
            status { isInternalServerError() }
        }
    }

    @Test
    fun `should return 204 status code when movie is found and deleted`() {
        whenever(movieService.delete(any())).thenReturn(true)

        mockMvc.delete("/movie/{id}", 1).andExpect {
            status { isNoContent() }
        }
    }

    @Test
    fun `should return 404 status code when movie is not found to delete`() {
        whenever(movieService.delete(any())).thenReturn(false)

        mockMvc.delete("/movie/{id}", 1).andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun `should return 500 status code when error is occurred while deleting movie`() {
        whenever(movieService.delete(any())).thenThrow(RuntimeException())

        mockMvc.delete("/movie/{id}", 1).andExpect {
            status { isInternalServerError() }
        }
    }

    @Test
    fun `should return a OMDB movie when title is found`() {
        val omdbMovie = mockOMDBMovie()
        runBlocking {
            launch {
                whenever(movieService.search(any())).thenReturn(omdbMovie)

                mockMvc.get("/movie/search") {
                    param("title", "title")
                }.asyncDispatch()
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(omdbMovie))
                    }
                }
            }
        }
    }

    @Test
    fun `should return 500 status code when error is occurred while getting OMDB movie`() {
        runBlocking {
            launch {
                whenever(movieService.search(any())).thenThrow(RuntimeException())

                mockMvc.get("/movie/search") {
                    param("title", "title")
                }.asyncDispatch()
                .andExpect {
                    status { isInternalServerError() }
                }
            }
        }
    }
}