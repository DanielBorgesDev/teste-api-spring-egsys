package com.egsys.taskapi.application.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime


@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    data class ErrorResponse(
        val timestamp: String = LocalDateTime.now().toString(),
        val status: Int,
        val error: String,
        val message: String
    )

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleNotFound(ex: ResourceNotFoundException): ResponseEntity<ErrorResponse> {
        log.warn("Resource not found: ${ex.message}")
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse(status = 404, error = "Not Found", message = ex.message ?: ""))
    }

    @ExceptionHandler(ResourceAlreadyExistsException::class)
    fun handleConflict(ex: ResourceAlreadyExistsException): ResponseEntity<ErrorResponse> {
        log.warn("Conflict: ${ex.message}")
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ErrorResponse(status = 409, error = "Conflict", message = ex.message ?: ""))
    }

    @ExceptionHandler(InvalidRequestException::class)
    fun handleBadRequest(ex: InvalidRequestException): ResponseEntity<ErrorResponse> {
        log.warn("Bad request: ${ex.message}")
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(status = 400, error = "Bad Request", message = ex.message ?: ""))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errors = ex.bindingResult.fieldErrors.joinToString("; ") {
            "${it.field}: ${it.defaultMessage}"
        }
        log.warn("Validation error: $errors")
        return ResponseEntity
            .status(HttpStatus.UNPROCESSABLE_ENTITY)
            .body(ErrorResponse(status = 422, error = "Unprocessable Entity", message = errors))
    }

    @ExceptionHandler(org.springframework.data.mapping.PropertyReferenceException::class)
    fun handlePropertyReference(ex: org.springframework.data.mapping.PropertyReferenceException): ResponseEntity<ErrorResponse> {
        log.warn("Invalid sort property: ${ex.message}")
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(status = 400, error = "Bad Request", message = "O campo de ordenação '${ex.propertyName}' não existe"))
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneric(ex: Exception): ResponseEntity<ErrorResponse> {
        log.error("Unexpected error", ex)
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse(status = 500, error = "Internal Server Error", message = "Erro interno no servidor"))
    }
}
