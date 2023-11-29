package com.example.moviequotes.omdb

import org.springframework.stereotype.Service

@Service
class OMDBAdapterImpl(
    private val omdbWebClient: OMDBWebClient
) : OMDBAdapter {
    override suspend fun getMovie(title: String): OMDBMovie? = omdbWebClient.getMovie(title)

}