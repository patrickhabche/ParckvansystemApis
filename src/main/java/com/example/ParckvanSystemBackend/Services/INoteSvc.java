package com.example.ParckvanSystemBackend.Services;

import com.example.ParckvanSystemBackend.DTOs.NoteFilter;
import com.example.ParckvanSystemBackend.DTOs.PagingNoteResponse;
import com.example.ParckvanSystemBackend.Entities.Note;
import com.example.ParckvanSystemBackend.Exceptions.NoteNotFoundException;
import org.apache.catalina.filters.RequestFilter;

import java.util.List;

public interface INoteSvc {
    public Note GetById(int id) throws NoteNotFoundException;
    public void AddNote(Note note);
    public void EditNote(Note note) throws Exception;
    public void DeleteNote(int id) throws NoteNotFoundException;
    public void ConfirmNote(int id , float deposit ) throws Exception;
    public PagingNoteResponse GetPaging(NoteFilter filter , int pageSize , int pageNumber);

}
