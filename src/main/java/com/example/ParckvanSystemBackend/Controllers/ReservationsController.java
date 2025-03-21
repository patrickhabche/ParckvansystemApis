package com.example.ParckvanSystemBackend.Controllers;

import com.example.ParckvanSystemBackend.DTOs.*;
import com.example.ParckvanSystemBackend.Entities.Reservation;
import com.example.ParckvanSystemBackend.Exceptions.ReservationDateTakenException;
import com.example.ParckvanSystemBackend.Exceptions.ReservationNotFoundException;
import com.example.ParckvanSystemBackend.Services.IReservationSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")

public class ReservationsController  {

    public IReservationSvc _reservationSvc;

    @Autowired
    public ReservationsController(IReservationSvc reservationSvc){
        _reservationSvc = reservationSvc;
    }
    @GetMapping("/All")
    public List<Reservation> GetAll(){
        var reservations = _reservationSvc.GetAllReservations();
        return reservations;
    }

    @GetMapping("/single")
    public ReservationSingleResponse GetById(int id ){
        ReservationSingleResponse response = new ReservationSingleResponse();
        try{
             var reservation = _reservationSvc.GetById(id);
             response.setReservation(reservation);
             response.setCode(200);
             response.setMessage("success");
        }

        catch (ReservationNotFoundException ex ) {
            response.setReservation(null);
            response.setCode(404);
            response.setMessage(ex.getMessage());
        }

        catch (Exception ex) { // Catch all other checked exceptions
            response.setReservation(null);
            response.setCode(500);
            response.setMessage("Internal Server Error: " + ex.getMessage());
        }
        return response;
    }

    @PostMapping("/create")
    public ReservationSaveResponse Add(@RequestBody Reservation reservation) throws ReservationDateTakenException{
        ReservationSaveResponse response = new ReservationSaveResponse();

        try {
            _reservationSvc.AddReservation(reservation);
            response.setCode(200);
            response.setMessage("Reservation added succesfully");

        }catch (ReservationDateTakenException ex){
            response.setCode(400);
            response.setMessage(ex.getMessage());
        }
        catch (Exception exception){
            response.setCode(500);
            response.setMessage("Internal Server Error: " + exception.getMessage());
        }
       return  response;
    }

    @PostMapping("/update")
    public ReservationSaveResponse Edit(@RequestBody Reservation reservation ) throws Exception{
        ReservationSaveResponse response = new ReservationSaveResponse();
        try {
            _reservationSvc.EditReservation(reservation);
            response.code = 200;
            response.message ="Updated succesfully";
        }
        catch (Exception exception){
            response.code = exception.hashCode();
            response.message = exception.getMessage();
        }
        return response;

    }
    @GetMapping("/delete")
    public DeleteResponse Delete(int id ){
        DeleteResponse response = new DeleteResponse();
        try
        {
            _reservationSvc.DeleteReservation(id);
            response.code = 200;
            response.message = "Reservation "+id+" successfully deleted";
        }
        catch (ReservationNotFoundException exception){
            response.code = 404;
            response.message = exception.getMessage();
        }

        return response;
    }
    @PostMapping("/paged")
    public PagingReservationResponse GetPagedResult(@RequestBody PagingReservationRequest request ){
        PagingReservationResponse response = new PagingReservationResponse();
         var paginatedResult = _reservationSvc.GetPaging(request.filter,request.pageSize,request.pageNumber);
        response.filter=request.filter;
        response.pageNumber = request.pageNumber;
        response.pageSize = request.pageSize;
        response.results = paginatedResult.getResults();
        response.totalRecords = paginatedResult.getTotalRecords();

        return response;
    }
}
