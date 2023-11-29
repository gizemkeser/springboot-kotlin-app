package com.example.moviequotes.omdb

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "omdb.api")
data class OMDBConfigurationProperties(
    val baseUrl: String,
    val apiKey: String,
)