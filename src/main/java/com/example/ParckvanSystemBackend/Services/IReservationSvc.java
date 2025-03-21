package com.example.ParckvanSystemBackend.Services;

import com.example.ParckvanSystemBackend.DTOs.PaginatedResult;
import com.example.ParckvanSystemBackend.DTOs.PagingReservationResponse;
import com.example.ParckvanSystemBackend.DTOs.ReservationFilter;
import com.example.ParckvanSystemBackend.Entities.Reservation;

import java.util.List;

public interface IReservationSvc {

    public Reservation GetById(int id) throws Exception;

    public List<Reservation> GetAllReservations();

    public void EditReservation(Reservation reservation) throws Exception;
    public void AddReservation(Reservation reservation) throws Exception;

    public void DeleteReservation(int id);
    public PaginatedResult<Reservation> GetPaging(ReservationFilter filter , int pageSize , int pageNumber);

}
