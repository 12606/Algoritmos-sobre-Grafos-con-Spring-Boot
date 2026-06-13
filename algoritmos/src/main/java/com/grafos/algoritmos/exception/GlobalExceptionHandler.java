package com.grafos.algoritmos.exception;

import com.grafos.algoritmos.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidGraphException.class)
    public ErrorResponse handleInvalidGraph(
            InvalidGraphException ex,
            HttpServletRequest request
    ) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse handleInvalidJson(
            HttpMessageNotReadableException ex,
            HttpServletRequest request
    ) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("JSON inválido")
                .message("El cuerpo de la petición no tiene un formato JSON válido.")
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleGeneralError(
            Exception ex,
            HttpServletRequest request
    ) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message("Ocurrió un error inesperado: " + ex.getMessage())
                .path(request.getRequestURI())
                .build();
    }
}