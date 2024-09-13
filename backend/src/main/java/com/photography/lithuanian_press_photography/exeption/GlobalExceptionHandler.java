package com.photography.lithuanian_press_photography.exeption;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleEntityNotFoundException(EntityNotFoundException exception) {
        ProblemDetail response = ProblemDetail.forStatus(404);
        response.setTitle("Entity Not Found");
        response.setStatus(response.getStatus());
        response.setDetail(exception.getMessage());
        return response;
    }

    @ExceptionHandler(EntityExistsException.class)
    public ProblemDetail handleEntityExistsException(EntityExistsException exception) {
        ProblemDetail response = ProblemDetail.forStatus(409);
        response.setTitle("Entity Exists");
        response.setStatus(response.getStatus());
        response.setDetail(exception.getMessage());
        return response;
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ProblemDetail handleStorageFileNotFound(StorageFileNotFoundException exception) {
        ProblemDetail response = ProblemDetail.forStatus(404);
        response.setTitle("File Not Found");
        response.setStatus(response.getStatus());
        response.setDetail(exception.getMessage());
        return response;
    }

    @ExceptionHandler(StorageException.class)
    public ProblemDetail handleStorageException(StorageException exception) {
        ProblemDetail response = ProblemDetail.forStatus(400);
        response.setTitle("Image Storage Error");
        response.setStatus(response.getStatus());
        response.setDetail(exception.getMessage());
        return response;
    }

    @ExceptionHandler({ImageValidationException.class})
    public ProblemDetail handleImageValidationError(ImageValidationException exception) {
        ProblemDetail response = ProblemDetail.forStatus(400);
        response.setTitle("Image Validation Error");
        response.setStatus(response.getStatus());
        response.setDetail(exception.getMessage());
        return response;
    }

    @ExceptionHandler({ImageProcessingException.class})
    public ProblemDetail handleImageProcessingError(ImageProcessingException exception) {
        ProblemDetail response = ProblemDetail.forStatus(400);
        response.setTitle("Image Processing Error");
        response.setStatus(response.getStatus());
        response.setDetail(exception.getMessage());
        return response;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        ProblemDetail response = ProblemDetail.forStatus(400);
        response.setTitle("Validation Error");
        response.setStatus(response.getStatus());
        response.setDetail("One or more fields contain invalid data.");
        response.setProperty("errors", errors);
        return response;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        ProblemDetail response = ProblemDetail.forStatus(400);
        response.setTitle("Parsing Error");
        response.setStatus(response.getStatus());
        response.setDetail(exception.getMessage());
        return response;
    }
}
