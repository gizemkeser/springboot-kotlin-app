package com.example.moviequotes.quote

import org.springframework.stereotype.Service

@Service
class QuoteServiceImpl(private val quoteRepository: QuoteRepository) : QuoteService {
    override fun findAll(): List<Quote> = quoteRepository.findAll().toList()
    override fun findById(id: Long): Quote? = quoteRepository.findById(id).orElse(null)
    override fun save(quote: Quote): Quote = quoteRepository.save(quote)
    override fun delete(id: Long): Boolean =
        if (quoteRepository.existsById(id)) {
            quoteRepository.deleteById(id)
            true
        } else false
}