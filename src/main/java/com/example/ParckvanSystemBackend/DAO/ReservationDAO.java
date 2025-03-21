package com.example.ParckvanSystemBackend.DAO;

import com.example.ParckvanSystemBackend.DTOs.PaginatedResult;
import com.example.ParckvanSystemBackend.DTOs.ReservationFilter;
import com.example.ParckvanSystemBackend.Entities.Reservation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ReservationDAO implements IReservationDAO{

    private EntityManager _entityManager;

    @Autowired
    public ReservationDAO(EntityManager entityManager){
        _entityManager = entityManager;
    }

    @Override
    public Reservation GetById(int id){
       Reservation toRet =  _entityManager.find(Reservation.class,id);
        return toRet;
    }

    @Override
    public List<Reservation> GetAll(){
        TypedQuery<Reservation> theQuery = _entityManager.createQuery("FROM Reservation",Reservation.class); //HQL hibernate query language
        var toRet  =  theQuery.getResultList();
        return toRet;
    }

    public List<Reservation> GetByDate(LocalDateTime checkinDate, LocalDateTime checkoutDate) {

        String query = "SELECT r FROM Reservation r WHERE " +
                "(r.checkin >= :checkinDate AND r.checkin < :checkoutDate) OR " +
                "(r.checkout > :checkinDate AND r.checkout <= :checkoutDate) OR " +
                "(r.checkin <= :checkinDate AND r.checkout >= :checkoutDate)";

        TypedQuery<Reservation> typedQuery = _entityManager.createQuery(query, Reservation.class);
        typedQuery.setParameter("checkinDate", checkinDate);
        typedQuery.setParameter("checkoutDate", checkoutDate);

        return typedQuery.getResultList();
    }


     @Transactional
     @Override
    public void  Create(Reservation reservation){
        _entityManager.persist(reservation);
    }

    @Transactional
    @Override
    public void Edit(Reservation reservation){
        _entityManager.merge(reservation);
    }


    @Override
    public PaginatedResult<Reservation> GetPaging(String queryString, ReservationFilter filter, int pageSize, int pageNumber) {
        // Query for retrieving paginated records
        TypedQuery<Reservation> query = _entityManager.createQuery(queryString, Reservation.class);

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

        List<Reservation> results = query.getResultList();

        // Return paginated results along with the total count
        return new PaginatedResult<>(results, totalRecords);
    }

}
