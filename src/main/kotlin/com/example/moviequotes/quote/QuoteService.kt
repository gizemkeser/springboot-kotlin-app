package com.example.moviequotes.quote

interface QuoteService {
    fun findAll(): List<Quote>
    fun findById(id: Long): Quote?
    fun save(quote: Quote): Quote
    fun delete(id: Long): Boolean
}