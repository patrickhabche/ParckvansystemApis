package com.example.ParckvanSystemBackend.DTOs;

import com.example.ParckvanSystemBackend.Entities.Reservation;

import java.util.List;

public class PaginatedResult<T> {
    private List<T> results;
    private long totalRecords;

    public PaginatedResult(List<T> results, long totalRecords) {
        this.results = results;
        this.totalRecords = totalRecords;
    }

    public List<T> getResults() {
        return results;
    }

    public long getTotalRecords() {
        return totalRecords;
    }
}

