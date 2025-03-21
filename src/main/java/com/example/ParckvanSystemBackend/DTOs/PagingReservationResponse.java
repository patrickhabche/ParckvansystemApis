package com.example.ParckvanSystemBackend.DTOs;

import com.example.ParckvanSystemBackend.Entities.Reservation;

import java.util.List;

public class PagingReservationResponse {
    public ReservationFilter filter;
    public long totalRecords;
    public int pageSize;
    public int pageNumber;
    public List<Reservation> results;

}
