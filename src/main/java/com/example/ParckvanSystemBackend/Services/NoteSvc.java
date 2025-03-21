package com.example.ParckvanSystemBackend.Services;

import com.example.ParckvanSystemBackend.DAO.INoteDAO;
import com.example.ParckvanSystemBackend.DTOs.NoteFilter;
import com.example.ParckvanSystemBackend.DTOs.PagingNoteResponse;
import com.example.ParckvanSystemBackend.Entities.Note;
import com.example.ParckvanSystemBackend.Entities.Reservation;
import com.example.ParckvanSystemBackend.Exceptions.NoteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;


@Service
public class NoteSvc implements INoteSvc {
    public INoteDAO _noteDAO;

    public IReservationSvc _reservationSvc;

    @Autowired
    public NoteSvc(INoteDAO _noteDAO ,@Lazy IReservationSvc reservationSvc) {
        this._noteDAO = _noteDAO;
        _reservationSvc  = reservationSvc;
    }

    @Override
    public Note GetById(int id) throws NoteNotFoundException {
      var toRet =   _noteDAO.GetSingle(id);
      if(toRet == null){
          throw new NoteNotFoundException("note found");
      }
      return toRet;
    }

    @Override
    public void AddNote(Note note) {
        note.setCreationTime(LocalDateTime.now());
        note.setLastModificationTime(LocalDateTime.now());
        _noteDAO.Add(note);
    }

    @Override
    public void EditNote(Note note) throws Exception {

        var toEdit = _noteDAO.GetSingle(note.getId());
        if(toEdit == null){
            throw new Exception("the note with id "+note.getId() +" does not exits");
        }

        toEdit.setCustomerName(note.getCustomerName());
        toEdit.setCheckin(note.getCheckin());
        toEdit.setCheckout(note.getCheckout());
        toEdit.setNumberOfPersons(note.getNumberOfPersons());
        toEdit.setAmount(note.getAmount());
        toEdit.setPhone(note.getPhone());

        // Tracking fields
        toEdit.setLastModificationTime(LocalDateTime.now());
        _noteDAO.Edit(toEdit);
    }

    @Override
    public  void DeleteNote(int id) throws NoteNotFoundException{
        var noteToDelete = _noteDAO.GetSingle(id);

        if(noteToDelete!= null){
           noteToDelete.setDeleted(true);
           noteToDelete.setDeletionTime(LocalDateTime.now());
           _noteDAO.Edit(noteToDelete);
           return;
        }
        throw new NoteNotFoundException("note with id :"+id +"not found");
    }

    @Override
    public void ConfirmNote(int id , float deposit ) throws Exception{

        try {
            var note = _noteDAO.GetSingle(id);

            // Create a reservation same info as the note
            Reservation res = new Reservation();

            res.setPhone(note.getPhone());
            res.setAmount(note.getAmount());
            res.setCustomerName(note.getCustomerName());
            res.setNumberOfPersons(note.getNumberOfPersons());
            res.setCheckin(note.getCheckin());
            res.setCheckout(note.getCheckout());
            res.setCreationTime(LocalDateTime.now());

            res.setDeposit(deposit);

            res.setNoteId(note.getId());
            _reservationSvc.AddReservation(res);


            //Set the note as confirmed
            note.setConfirmed(true);
            _noteDAO.Edit(note);
        }
        catch (Exception ex ){
            throw new Exception(ex.getMessage());
        }


    }

    @Override
    public PagingNoteResponse GetPaging(NoteFilter filter, int pageSize , int pageNumber) {
        var queryString  = BuildFilter(filter);
        var paginatedResult = _noteDAO.GetPaging(queryString,filter,pageSize,pageNumber);
        PagingNoteResponse response = new PagingNoteResponse();
        response.filter = filter;
        response.filter = filter;
        response.pageNumber = pageNumber;
        response.pageSize  = pageSize;
        response.results = paginatedResult.getResults();
        response.totalRecords = paginatedResult.getTotalRecords();


        return response ;
    }

    private String BuildFilter(NoteFilter filter){

        String queryString = "FROM Note r WHERE 1=1 AND r.isDeleted = false AND r.confirmed = false"; // Base query

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
}
