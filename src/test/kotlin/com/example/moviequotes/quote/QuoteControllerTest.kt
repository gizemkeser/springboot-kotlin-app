package com.example.moviequotes.quote

import com.example.moviequotes.utils.mockQuote
import com.example.moviequotes.utils.mockQuoteList
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.util.*

@WebMvcTest(QuoteController::class)
class QuoteControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper
) {
    @MockBean
    private lateinit var quoteService: QuoteService

    @Test
    fun `should add a quote when a quote is posted`() {
        val quote = mockQuote()
        whenever(quoteService.save(quote)).thenReturn(quote)

        mockMvc.post("/quote") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(quote)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isCreated() }
            content {
                contentType(MediaType.APPLICATION_JSON)
                json(objectMapper.writeValueAsString(quote))
            }
        }
    }

    @Test
    fun `should return 500 status code when a problem occurred while posting a quote`() {
        val quote = mockQuote()
        whenever(quoteService.save(quote)).thenThrow(RuntimeException())

        mockMvc.post("/quote") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(quote)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isInternalServerError() }
        }
    }

    @Test
    fun `should return all quotes when all quotes are requested`() {
        val quoteList = mockQuoteList()
        whenever(quoteService.findAll()).thenReturn(quoteList)

        mockMvc.get("/quote").andExpect {
            status { isOk() }
            content {
                contentType(MediaType.APPLICATION_JSON)
                json(objectMapper.writeValueAsString(quoteList))
            }
        }
    }

    @Test
    fun `should return 500 status code when a problem occurred while all quotes are requested`() {
        whenever(quoteService.findAll()).thenThrow(RuntimeException())

        mockMvc.get("/quote").andExpect {
            status { isInternalServerError() }
        }
    }


    @Test
    fun `should return a quote when quote is found for the given id`() {
        val quote = mockQuote()
        whenever(quoteService.findById(any())).thenReturn(quote)

        mockMvc.get("/quote/{id}", 1).andExpect {
            status { isOk() }
            content {
                contentType(MediaType.APPLICATION_JSON)
                json(objectMapper.writeValueAsString(quote))
            }
        }
    }

    @Test
    fun `should return 404 status code when quote is not found for the given id`() {
        whenever(quoteService.findById(any())).thenReturn(null)

        mockMvc.get("/quote/{id}", 1).andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun `should return 500 status code when error is occurred while finding quote`() {
        whenever(quoteService.findById(any())).thenThrow(RuntimeException())

        mockMvc.get("/quote/{id}", 1).andExpect {
            status { isInternalServerError() }
        }
    }

    @Test
    fun `should return 204 status code when quote is found and deleted`() {
        whenever(quoteService.delete(any())).thenReturn(true)

        mockMvc.delete("/quote/{id}", 1).andExpect {
            status { isNoContent() }
        }
    }


    @Test
    fun `should return 404 status code when quote is not found to delete`() {
        whenever(quoteService.delete(any())).thenReturn(false)

        mockMvc.delete("/quote/{id}", 1).andExpect {
            status { isNotFound() }
        }
    }


    @Test
    fun `should return 500 status code when error is occurred while deleting quote`() {
        whenever(quoteService.delete(any())).thenThrow(RuntimeException())

        mockMvc.delete("/quote/{id}", 1).andExpect {
            status { isInternalServerError() }
        }
    }
}