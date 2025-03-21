package com.example.ParckvanSystemBackend.DAO;

import com.example.ParckvanSystemBackend.DTOs.PaginatedResult;
import com.example.ParckvanSystemBackend.DTOs.ReservationFilter;
import com.example.ParckvanSystemBackend.Entities.Reservation;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface IReservationDAO {
    public Reservation GetById(int id);
    public List<Reservation> GetAll();
    public List<Reservation> GetByDate(LocalDateTime checkin, LocalDateTime checkout);
    public void  Create(Reservation reservation);
    public void Edit(Reservation reservation);
    public PaginatedResult<Reservation> GetPaging(String queryString , ReservationFilter filter, int pageSize , int pageNumber);

}
