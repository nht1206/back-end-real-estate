package com.codegym.webservice.exceptionhandler;

import com.codegym.webservice.payload.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exceptionHandle(Exception ex) {
        ErrorResponse customerErrorResponse = new ErrorResponse();
        customerErrorResponse.setMessage(ex.getMessage());
        customerErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        customerErrorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(customerErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
