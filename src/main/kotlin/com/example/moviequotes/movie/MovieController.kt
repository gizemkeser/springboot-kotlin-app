package com.example.moviequotes.movie

import com.example.moviequotes.omdb.OMDBMovie
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/movie")
class MovieController(
    private val movieService: MovieService
) {
    @PostMapping
    fun add(@RequestBody newMovie: Movie): ResponseEntity<Movie> {
        val savedMovie = movieService.save(newMovie)
        return ResponseEntity(savedMovie, HttpStatus.CREATED)
    }

    @GetMapping
    fun getAll(): List<Movie> = movieService.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Movie> {
        val movie = movieService.findById(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(movie)
    }

    @GetMapping("/search")
    suspend fun search(@RequestParam title: String): ResponseEntity<OMDBMovie> {
        val omdbMovie = movieService.search(title)?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(omdbMovie)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> =
        if(movieService.delete(id)) {
             ResponseEntity.noContent().build()
        }
        else ResponseEntity.notFound().build()

}