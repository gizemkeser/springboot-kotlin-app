package com.example.moviequotes.quote

import com.example.moviequotes.utils.mockQuote
import com.example.moviequotes.utils.mockQuoteList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class QuoteServiceImplTest {
    @Mock
    private lateinit var quoteRepository: QuoteRepository
    @InjectMocks
    private lateinit var quoteServiceImpl: QuoteServiceImpl

    @Test
    fun `should return all quotes when all quotes are requested`() {
        val quoteList = mockQuoteList()
        whenever(quoteRepository.findAll()).thenReturn(quoteList)
        Assertions.assertEquals(quoteList, quoteServiceImpl.findAll())
    }

    @Test
    fun `should return quote when the quote is find for the given id`() {
        val quote = Optional.of(mockQuote())
        whenever(quoteRepository.findById(any())).thenReturn(quote)
        Assertions.assertEquals(quote.get(), quoteServiceImpl.findById(1))
    }

    @Test
    fun `should return null when the quote is not find for the given id`() {
        whenever(quoteRepository.findById(any())).thenReturn(Optional.empty())
        Assertions.assertEquals(null, quoteServiceImpl.findById(1))
    }

    @Test
    fun `should add a quote when a quote is posted`() {
        val quote = mockQuote()
        whenever(quoteRepository.save(quote)).thenReturn(quote)
        Assertions.assertEquals(quote, quoteServiceImpl.save(quote))
    }

    @Test
    fun `should return true when quote to be deleted is found`() {
        whenever(quoteRepository.existsById(any())).thenReturn(true)
        Assertions.assertEquals(true, quoteServiceImpl.delete(1))
    }

    @Test
    fun `should return false when quote to be deleted is not found`() {
        whenever(quoteRepository.existsById(any())).thenReturn(false)
        Assertions.assertEquals(false, quoteServiceImpl.delete(1))
    }
}