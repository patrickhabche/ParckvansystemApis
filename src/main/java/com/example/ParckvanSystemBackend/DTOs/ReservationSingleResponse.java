package com.example.ParckvanSystemBackend.DTOs;

import com.example.ParckvanSystemBackend.Entities.Reservation;

public class ReservationSingleResponse {
    public int code;
    public Reservation reservation;

    public String message;


    public ReservationSingleResponse(int code, Reservation reservation , String message) {
        this.code = code;
        this.reservation = reservation;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ReservationSingleResponse() {
    }

    public Reservation getReservation() {
        return reservation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
