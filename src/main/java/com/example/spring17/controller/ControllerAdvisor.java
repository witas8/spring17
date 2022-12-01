package com.example.spring17.controller;

import com.example.spring17.exceptions.BadRequestException;
import com.example.spring17.exceptions.NotFoundException;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.example.spring17.utils.Constants.CONTROLLER_ADVISOR_BEGINNING;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ BadRequestException.class })
    public ResponseEntity<ErrorMessage> handleBadRequestException(BadRequestException ex) {
        ErrorMessage message = new ErrorMessage(CONTROLLER_ADVISOR_BEGINNING
                + HttpStatus.BAD_REQUEST + " " + ex.getMessage());

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundException ex) {
        ErrorMessage message = new ErrorMessage(CONTROLLER_ADVISOR_BEGINNING
                + HttpStatus.NOT_FOUND + " " + ex.getMessage());

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
