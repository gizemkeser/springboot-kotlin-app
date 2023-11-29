package com.example.moviequotes.exception

import org.springframework.http.HttpStatus
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception) =
        ErrorResponse.create(e, HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred.")
}