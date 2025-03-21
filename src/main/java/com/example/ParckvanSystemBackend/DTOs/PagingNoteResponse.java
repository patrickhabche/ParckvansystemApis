package com.example.ParckvanSystemBackend.DTOs;

import com.example.ParckvanSystemBackend.Entities.Note;
import com.example.ParckvanSystemBackend.Entities.Reservation;

import java.util.List;

public class PagingNoteResponse {
    public NoteFilter filter;
    public long totalRecords;
    public int pageSize;
    public int pageNumber;
    public List<Note> results;

}
