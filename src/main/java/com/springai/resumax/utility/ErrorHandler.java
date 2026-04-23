package com.springai.resumax.utility;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
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

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> userNameNotFoundExceptionHandler(UsernameNotFoundException exception){

        String errorMsg = exception.getLocalizedMessage();

        Map<String,String> error = new HashMap<>();

        error.put("Error",errorMsg);

        return ResponseHandler.builder(null,error,HttpStatus.NOT_FOUND,LocalDateTime.now());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> authenticationExceptionHandler(AuthenticationException exception){

        String errorMsg = exception.getLocalizedMessage();

        Map<String,String> error = new HashMap<>();

        error.put("Error",errorMsg);

        return ResponseHandler.builder(null,error,HttpStatus.UNAUTHORIZED,LocalDateTime.now());
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> jwtExceptionHandler(JwtException exception){

        String errorMsg = exception.getLocalizedMessage();

        Map<String,String> error = new HashMap<>();

        error.put("Error",errorMsg);

        return ResponseHandler.builder(null,error,HttpStatus.UNAUTHORIZED,LocalDateTime.now());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException exception){


        String errorMsg = exception.getLocalizedMessage();

        Map<String,String> error = new HashMap<>();

        error.put("Error",errorMsg);

        return ResponseHandler.builder(null,error,HttpStatus.FORBIDDEN,LocalDateTime.now());

    }

}
