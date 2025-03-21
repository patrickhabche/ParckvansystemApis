package com.example.ParckvanSystemBackend.DAO;

import com.example.ParckvanSystemBackend.DTOs.NoteFilter;
import com.example.ParckvanSystemBackend.DTOs.PaginatedResult;
import com.example.ParckvanSystemBackend.DTOs.ReservationFilter;
import com.example.ParckvanSystemBackend.Entities.Note;
import com.example.ParckvanSystemBackend.Entities.Reservation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NoteDAO implements INoteDAO {

    private EntityManager _entityManager;
    @Autowired
    public NoteDAO(EntityManager entityManager) {
        _entityManager = entityManager;
    }

    @Transactional
    @Override
    public void Add(Note note) {
        _entityManager.persist(note);
    }

    @Transactional
    @Override
    public Note Edit(Note note) {
        _entityManager.merge(note);
        return note;
    }

    @Override
    public Note GetSingle(int id) {
        return _entityManager.find(Note.class,id);
    }


    @Override
    public PaginatedResult<Note> GetPaging(String queryString, NoteFilter filter, int pageSize, int pageNumber) {
        // Query for retrieving paginated records
        TypedQuery<Note> query = _entityManager.createQuery(queryString, Note.class);

        // Count query for getting the total number of matching records
        String countQueryString = "SELECT COUNT(r) " + queryString.substring(queryString.indexOf("FROM"));
        TypedQuery<Long> countQuery = _entityManager.createQuery(countQueryString, Long.class);

        // Set parameters if filter is provided
        if (filter != null) {
            if (filter.customerName != null && !filter.customerName.isEmpty()) {
                query.setParameter("customerName", "%" + filter.customerName + "%");
                countQuery.setParameter("customerName", "%" + filter.customerName + "%");
            }
            if (filter.checkin != null) {
                query.setParameter("checkin", filter.checkin);
                countQuery.setParameter("checkin", filter.checkin);
            }
            if (filter.checkout != null) {
                query.setParameter("checkout", filter.checkout);
                countQuery.setParameter("checkout", filter.checkout);
            }
            if (filter.phone != null && !filter.phone.isEmpty()) {
                query.setParameter("phone", "%" + filter.phone + "%");
                countQuery.setParameter("phone", "%" + filter.phone + "%");
            }
        }

        // Execute count query to get total records
        long totalRecords = countQuery.getSingleResult();

        // Apply pagination
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);

        List<Note> results = query.getResultList();

        // Return paginated results along with the total count
        return new PaginatedResult<>(results, totalRecords);
    }



}
