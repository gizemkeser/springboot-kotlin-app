package com.example.moviequotes

import com.example.moviequotes.omdb.OMDBConfigurationProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(OMDBConfigurationProperties::class)
@SpringBootApplication
class MovieQuotesApplication

fun main(args: Array<String>) {
	runApplication<MovieQuotesApplication>(*args)
}
