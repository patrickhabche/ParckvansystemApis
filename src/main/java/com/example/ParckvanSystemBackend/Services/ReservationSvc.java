package com.example.ParckvanSystemBackend.Services;

import com.example.ParckvanSystemBackend.DAO.IReservationDAO;
import com.example.ParckvanSystemBackend.DTOs.PaginatedResult;
import com.example.ParckvanSystemBackend.DTOs.PagingReservationResponse;
import com.example.ParckvanSystemBackend.DTOs.ReservationFilter;
import com.example.ParckvanSystemBackend.DTOs.ReservationSingleResponse;
import com.example.ParckvanSystemBackend.Entities.Note;
import com.example.ParckvanSystemBackend.Entities.Reservation;
import com.example.ParckvanSystemBackend.Exceptions.ReservationDateTakenException;
import com.example.ParckvanSystemBackend.Exceptions.ReservationNotFoundException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReservationSvc implements IReservationSvc{

    public IReservationDAO _reservationDAO;
    public INoteSvc _noteSvc;

    @Autowired
    public ReservationSvc(IReservationDAO reservationDAO , INoteSvc noteSvc){
        _reservationDAO = reservationDAO;
        _noteSvc = noteSvc;
    }
    @Override
    public Reservation GetById(int id) throws ReservationNotFoundException {
        var toRet = _reservationDAO.GetById(id);
        if(toRet == null){
            throw new ReservationNotFoundException("Reservation with id " + id+ " not found");
        }
        return toRet;
    }

    @Override
    public List<Reservation> GetAllReservations() {
       var toRet = _reservationDAO.GetAll();
       return toRet;
    }

    @Override
    public void EditReservation(Reservation reservation) throws Exception {
        var toEdit = _reservationDAO.GetById(reservation.getId());
        if(toEdit == null){
            throw new Exception("the reservation with id "+reservation.getId() +" does not exits");
        }

        // Check if check in and checkout are available
        CheckReservationDate(reservation,reservation.getId());
        toEdit.setCustomerName(reservation.getCustomerName());
        toEdit.setCheckin(reservation.getCheckin());
        toEdit.setCheckout(reservation.getCheckout());
        toEdit.setNumberOfPersons(reservation.getNumberOfPersons());
        toEdit.setDeposit(reservation.getDeposit());
        toEdit.setAmount(reservation.getAmount());
        toEdit.setPhone(reservation.getPhone());

        // Tracking fields
        toEdit.setLastModificationTime(LocalDateTime.now());


        _reservationDAO.Edit(toEdit);

    }

    @Override
    public void AddReservation(Reservation reservationToAdd) throws Exception {
             // Check if check in and checkout are available
             CheckReservationDate(reservationToAdd,-1);

            // if we are creating directly a reservation without a note manually create a note and add it a reference for the res
            Note note = new Note();
            note.setCheckin(reservationToAdd.getCheckin());
            note.setCheckout(reservationToAdd.getCheckout());
            note.setCustomerName(reservationToAdd.getCustomerName());
            note.setNumberOfPersons(reservationToAdd.getNumberOfPersons());
            note.setAmount(reservationToAdd.getAmount());
            note.setPhone(reservationToAdd.getPhone());
            note.setConfirmed(true);
            note.setCreationTime(LocalDateTime.now());
            note.setLastModificationTime(LocalDateTime.now());

            _noteSvc.AddNote(note);

            reservationToAdd.setNoteId(note.getId());
            // Tracking properties
            reservationToAdd.setCreationTime(LocalDateTime.now());
            reservationToAdd.setLastModificationTime(LocalDateTime.now());
            _reservationDAO.Create(reservationToAdd);

    }

    @Override
    public void DeleteReservation( int id) {
       var resToDelete =  _reservationDAO.GetById(id);
       if (resToDelete !=null){
           resToDelete.setDeleted(true);
           resToDelete.setDeletionTime(LocalDateTime.now());
           _reservationDAO.Edit(resToDelete);
           return;
       }

        throw new ReservationNotFoundException("Reservation "+id+" is not found");
    }

    @Override
    public PaginatedResult<Reservation> GetPaging(ReservationFilter filter , int pageSize , int pageNumber){
        var queryString  = BuildFilter(filter);
        var paginatedResult = _reservationDAO.GetPaging(queryString,filter,pageSize,pageNumber);

        return paginatedResult ;
    }

    private String BuildFilter(ReservationFilter filter){

        String queryString = "FROM Reservation r WHERE 1=1 AND r.isDeleted = false"; // Base query

        if (filter != null) {
            if (filter.customerName != null && !filter.customerName.isEmpty()) {
                queryString += " AND r.customerName LIKE :customerName";
            }
            if (filter.checkin != null) {
                queryString += " AND r.checkin = :checkin";
            }
            if (filter.checkout != null) {
                queryString += " AND r.checkout = :checkout";
            }
            if (filter.phone != null && !filter.phone.isEmpty()) {
                queryString += " AND r.phone LIKE :phone";
            }
        }

        return queryString;
    }

    private void CheckReservationDate(Reservation reservation,int id) throws  Exception{


        // Checkout must always be bigger than checkin
        if(reservation.getCheckin().isAfter(reservation.getCheckout()) ){
            throw new ReservationDateTakenException(
                    "The check-in must be before check-out "
            );
        }


        // Check checkin and checkout are not taken
        List<Reservation> existingReservations = _reservationDAO.GetByDate(reservation.getCheckin(), reservation.getCheckout());

        for (Reservation res : existingReservations) {
            LocalDateTime resCheckin = res.getCheckin();
            LocalDateTime resCheckout = res.getCheckout();

            // If editing, skip checking against itself
            if (res.getId() == id) {
                Reservation oldReservation = GetById(id);
                if (oldReservation != null && oldReservation.getCheckin().equals(resCheckin) && oldReservation.getCheckout().equals(resCheckout)) {
                    continue;
                }
            }

            LocalDateTime newCheckin = reservation.getCheckin();
            LocalDateTime newCheckout = reservation.getCheckout();

            // Case 1: Exact same check-in and check-out already taken
            if (newCheckin.equals(resCheckin) && newCheckout.equals(resCheckout)) {
                throw new ReservationDateTakenException(
                        "The check-in and check-out dates are already taken from " + resCheckin + " to " + resCheckout
                );
            }

            // Case 2: New reservation starts inside an existing reservation
            if (newCheckin.isAfter(resCheckin) && newCheckin.isBefore(resCheckout)) {
                throw new ReservationDateTakenException(
                        "Your check-in time " + newCheckin + " is within an existing reservation from " + resCheckin + " to " + resCheckout
                );
            }

            // Case 3: New reservation ends inside an existing reservation
            if (newCheckout.isAfter(resCheckin) && newCheckout.isBefore(resCheckout)) {
                throw new ReservationDateTakenException(
                        "Your check-out time " + newCheckout + " is within an existing reservation from " + resCheckin + " to " + resCheckout
                );
            }

            // Case 4: New reservation fully contains an existing reservation
            if (newCheckin.isBefore(resCheckin) && newCheckout.isAfter(resCheckout)) {
                throw new ReservationDateTakenException(
                        "Your reservation from " + newCheckin + " to " + newCheckout + " completely overlaps an existing one from " + resCheckin + " to " + resCheckout
                );
            }

            // Case 5: New reservation is fully contained within an existing reservation
            if (newCheckin.isAfter(resCheckin) && newCheckout.isBefore(resCheckout)) {
                throw new ReservationDateTakenException(
                        "Your reservation from " + newCheckin + " to " + newCheckout + " is fully contained within an existing one from " + resCheckin + " to " + resCheckout
                );
            }

            // Case 6: Overlapping at start (new reservation starts before but overlaps at the beginning)
            if (newCheckin.isBefore(resCheckin) && newCheckout.isAfter(resCheckin) && newCheckout.isBefore(resCheckout)) {
                throw new ReservationDateTakenException(
                        "Your reservation starts before an existing one but overlaps with it from " + resCheckin + " to " + resCheckout
                );
            }

            // Case 7: Overlapping at end (new reservation starts within an existing one but ends after)
            if (newCheckin.isAfter(resCheckin) && newCheckin.isBefore(resCheckout) && newCheckout.isAfter(resCheckout)) {
                throw new ReservationDateTakenException(
                        "Your reservation starts within an existing one but extends beyond it from " + resCheckin + " to " + resCheckout
                );
            }
        }
    }
}
