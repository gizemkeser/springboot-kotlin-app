package com.example.moviequotes.quote

import org.springframework.data.repository.CrudRepository

interface QuoteRepository: CrudRepository<Quote, Long>