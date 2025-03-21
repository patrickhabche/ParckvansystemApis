package com.example.ParckvanSystemBackend.Exceptions;

public class ReservationNotFoundException extends RuntimeException{

    public ReservationNotFoundException(String message){
        super(message);
    }
}
