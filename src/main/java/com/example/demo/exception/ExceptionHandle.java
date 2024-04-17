package com.example.demo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@RestControllerAdvice
public class ExceptionHandle {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity<Message> handleIllegalArgumentException(HttpClientErrorException e) {
        String errorMessage = "ERROR 400: Http Client Error";
        logger.error(errorMessage, e);
        return ResponseEntity.status(e.getStatusCode()).body(new Message(errorMessage, e.getMessage()));
    }


    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Message> handleIllegalArgumentException(IllegalArgumentException e) {
        String errorMessage = "Error 404: Illegal Argument";
        logger.error(errorMessage, e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Message(errorMessage, e.getMessage()));
    }


    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<Message> handleNoResourceFoundException(NoHandlerFoundException e) {
        String errorMessage = "ERROR 400: No Handler Found";
        logger.error(errorMessage, e);
        return ResponseEntity.status(e.getStatusCode()).body(new Message(errorMessage, e.getMessage()));
    }


    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<Message> handleMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        String errorMessage = "Error 405: Method Not Supported";
        logger.error(errorMessage, e);
        return ResponseEntity.status(e.getStatusCode()).body(new Message(errorMessage, e.getMessage()));
    }


    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Message> handlerRuntimeException(RuntimeException e) {
        String errorMessage = "Error 500: Runtime Exception";
        logger.error(errorMessage, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Message(errorMessage, e.getMessage()));
    }


    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<Message> handlerRuntimeException(
            MissingServletRequestParameterException e) {
        String errorMessage = "Error 400: Bad Request";
        logger.error(errorMessage, e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Message(errorMessage, e.getMessage()));
    }


    @ExceptionHandler({NoResourceFoundException.class})
    public ResponseEntity<Message> noResourceFoundException(NoResourceFoundException e) {
        String errorMessage = "ERROR 404: No Resource Found";
        logger.error(errorMessage, e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Message(errorMessage, e.getMessage()));
    }


    @ExceptionHandler({Exception.class})
    public ResponseEntity<Message> exception(Exception e) {
        String errorMessage = "Error 500: Unknown Exception";
        logger.error(errorMessage, e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Message(errorMessage, e.getMessage()));
    }

    private record Message(String message, String description) {
    }
}
