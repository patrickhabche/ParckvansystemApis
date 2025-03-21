package com.example.ParckvanSystemBackend.DAO;

import com.example.ParckvanSystemBackend.DTOs.NoteFilter;
import com.example.ParckvanSystemBackend.DTOs.PaginatedResult;
import com.example.ParckvanSystemBackend.Entities.Note;

import java.util.List;

public interface INoteDAO {

    public void Add(Note note);
    public Note Edit(Note note);
    public Note GetSingle(int id);
    public PaginatedResult<Note> GetPaging(String queryString, NoteFilter filter, int pageSize, int pageNumber);
}
