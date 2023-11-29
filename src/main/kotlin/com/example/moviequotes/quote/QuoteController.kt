package com.example.moviequotes.quote

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/quote")
class QuoteController(val quoteService: QuoteService) {
    @PostMapping
    fun add(@RequestBody newQuote: Quote): ResponseEntity<Quote> {
        val savedQuote = quoteService.save(newQuote)
        return ResponseEntity(savedQuote, HttpStatus.CREATED)
    }

    @GetMapping
    fun getAll(): List<Quote> = quoteService.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Quote> {
        val quote = quoteService.findById(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(quote)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<String> =
        if (quoteService.delete(id)) {
            ResponseEntity.noContent().build()
        } else ResponseEntity.status(HttpStatus.NOT_FOUND).body("Quote with ID $id not found")

}