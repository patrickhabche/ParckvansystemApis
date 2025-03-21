package com.example.ParckvanSystemBackend.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="reservations")
public class Reservation {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String customerName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime checkin;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime checkout;

    private int numberOfPersons;
    private float deposit;

    private float amount;
    private String phone;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getCheckin() {
        return checkin;
    }

    public void setCheckin(LocalDateTime checkin) {
        this.checkin = checkin;
    }

    public LocalDateTime getCheckout() {
        return checkout;
    }

    public void setCheckout(LocalDateTime checkout) {
        this.checkout = checkout;
    }
    private boolean isDeleted;
    private LocalDateTime deletionTime;
    private Integer  deleterUserId;
    private LocalDateTime lastModificationTime;
    private Integer  lastModifierUserId;
    private int creatorUserId;
    private LocalDateTime creationTime;

    private Integer noteId;

    public void setDeleterUserId(Integer deleterUserId) {
        this.deleterUserId = deleterUserId;
    }

    public void setLastModifierUserId(Integer lastModifierUserId) {
        this.lastModifierUserId = lastModifierUserId;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public LocalDateTime getDeletionTime() {
        return deletionTime;
    }

    public void setDeletionTime(LocalDateTime deletionTime) {
        this.deletionTime = deletionTime;
    }

    public Integer getDeleterUserId() {
        return deleterUserId;
    }

    public void setDeleterUserId(int deleterUserId) {
        this.deleterUserId = deleterUserId;
    }

    public LocalDateTime getLastModificationTime() {
        return lastModificationTime;
    }

    public void setLastModificationTime(LocalDateTime lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }

    public Integer getLastModifierUserId() {
        return lastModifierUserId;
    }

    public void setLastModifierUserId(int lastModifierUserId) {
        this.lastModifierUserId = lastModifierUserId;
    }

    public int getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(int creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public int getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(int numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public float getDeposit() {
        return deposit;
    }

    public void setDeposit(float deposit) {
        this.deposit = deposit;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Reservation(String customerName, LocalDateTime checkin, LocalDateTime checkout, int numberOfPersons, float deposit, float amount, String phone) {
        this.customerName = customerName;
        this.checkin = checkin;
        this.checkout = checkout;
        this.numberOfPersons = numberOfPersons;
        this.deposit = deposit;
        this.amount = amount;
        this.phone = phone;
    }
    public Reservation(){}
}
