package com.example.moviequotes.omdb

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Component
class OMDBWebClient(private val props: OMDBConfigurationProperties) {

    private val webClient: WebClient = WebClient
        .builder()
        .baseUrl(props.baseUrl)
        .build()

    suspend fun getMovie(title: String): OMDBMovie? {
        return webClient
            .get()
            .uri { uriBuilder ->
                uriBuilder
                    .queryParam("apikey", props.apiKey)
                    .queryParam("t", title).build()
            }
            .retrieve()
            .awaitBody()
    }
}