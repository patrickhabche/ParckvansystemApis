package com.example.ParckvanSystemBackend.Controllers;

import com.example.ParckvanSystemBackend.DTOs.*;
import com.example.ParckvanSystemBackend.Entities.Note;
import com.example.ParckvanSystemBackend.Exceptions.NoteNotFoundException;
import com.example.ParckvanSystemBackend.Services.INoteSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/nottes")

public class NotteController  {
    public INoteSvc _noteSvc;

    @Autowired
    public NotteController(INoteSvc noteSvc){
        _noteSvc = noteSvc;
    }
    @GetMapping("/single")
    public NoteSingleResponse GetById(int id ) throws NoteNotFoundException {
        NoteSingleResponse response = new NoteSingleResponse();
        try{
            var note = _noteSvc.GetById(id);
            response.code = 200;
            response.result = note;
            response.message = "Success";
        }
        catch (NoteNotFoundException exception){
            response.code = 404;
            response.result = null;
            response.message = "Not found";
        }
        catch (Exception exception){
            response.code = 500;
            response.result = null;
            response.message = exception.getMessage();
        }

        return response;
    }

    @PostMapping("/create")
    public NoteSaveResponse Add(@RequestBody Note note) throws Exception{
        NoteSaveResponse response = new NoteSaveResponse();
        try {
            _noteSvc.AddNote(note);
            response.code = 200;
            response.setMessage("Note is successfully saved");
        } catch (Exception ex) {
            response.code = 500;
            response.setMessage(ex.getMessage());
        }

        return response;
    }

    @PostMapping("/update")
    public NoteSaveResponse Edit(@RequestBody Note note ) throws NoteNotFoundException{
        NoteSaveResponse response = new NoteSaveResponse();
        try {
            _noteSvc.EditNote(note);
            response.code = 200;
            response.message = "success editing the note with id "+ note.getId();

        }

        catch (NoteNotFoundException ex){
            response.message = ex.getMessage();
            response.code =  404;
        }
        catch (Exception ex){
            response.message = ex.getMessage();
            response.code = 500;
        }
        return response;
    }
    @GetMapping("/delete")
    public DeleteResponse Delete(int id ) throws NoteNotFoundException{
        DeleteResponse response = new DeleteResponse();

        try{
            _noteSvc.DeleteNote(id);
            response.code = 200;
            response.message = " Note with id : "+id +" is deleted successfully";
        }
        catch (NoteNotFoundException exception){
            response.code = 404;
            response.message = exception.getMessage();
        }
        catch (Exception exception){
            response.code = 500;
            response.message = exception.getMessage();
        }

        return response;
    }
    @PostMapping("/paging")
    public PagingNoteResponse GetPagedResult(@RequestBody PagingNoteRequest request ){
        var toRet = _noteSvc.GetPaging(request.filter,request.pageSize,request.pageNumber);
        return toRet;
    }

    @PostMapping("/confirm")
    public ConfirmNoteResponse ConfirmNote(@RequestBody NoteConfirmationRequest request) throws Exception{

        ConfirmNoteResponse response = new ConfirmNoteResponse();
        try {
            _noteSvc.ConfirmNote(request.noteId,request.deposit);
            response.code = 200;
            response.message = "Successfully confirmed , a new reservation is added ";
        }
        catch (Exception ex){
            response.code = 500;
            response.message = ex.getMessage();
        }

        return  response;
    }
}
