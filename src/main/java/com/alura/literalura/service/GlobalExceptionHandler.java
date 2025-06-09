package com.alura.literalura.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public void handleDataIntegrityViolation(DataIntegrityViolationException e) {
        System.err.println("Error de integridad de datos: " + e.getMessage());
        System.out.println("Este libro o autor ya existe en la base de datos.");
    }

    @ExceptionHandler(Exception.class)
    public void handleGenericException(Exception e) {
        System.err.println("Error inesperado: " + e.getMessage());
        e.printStackTrace();
    }
}
