package com.springai.resumax.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> noResourceFound(IllegalArgumentException exception){

        Map<String,String> error = new HashMap<>();

        error.put("Error",exception.getLocalizedMessage());

        return ResponseHandler.builder(null,error, HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }

    @ExceptionHandler(InternalError.class)
    public ResponseEntity<?> internalServerError(Exception e){

        Map<String,String> error = new HashMap<>();

        error.put("Error",e.getLocalizedMessage());

        return ResponseHandler.builder(null,error, HttpStatus.BAD_REQUEST, LocalDateTime.now());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validationErrorHandler(MethodArgumentNotValidException exception){

        Map<String,String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors()
                .forEach(error->
                        errors.put(
                        error.getField(),
                        error.getDefaultMessage()
                ));

        return ResponseHandler.builder(null,errors,HttpStatus.OK,LocalDateTime.now());
    }
}
