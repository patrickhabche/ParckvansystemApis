package com.example.ParckvanSystemBackend.Exceptions;

import com.example.ParckvanSystemBackend.Entities.Reservation;
import com.example.ParckvanSystemBackend.Errors.ReservationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ReservationExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ReservationErrorResponse> handleException(ReservationNotFoundException exc) {
        ReservationErrorResponse error = new ReservationErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
