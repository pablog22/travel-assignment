package com.afkl.travel.exercise.web;

import com.afkl.travel.exercise.exception.LocationNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TravelControllerExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(TravelControllerExceptionHandler.class);

    @ExceptionHandler(LocationNotFoundException.class)
    public ResponseEntity<String> handleDiffException(LocationNotFoundException exception) {
        log.warn("Location not found.");

        return new ResponseEntity<>("Location not found.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleDefaultException(Throwable throwable) {
        log.error("An exception occurred.", throwable);

        String message = "Unexpected server error";
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
