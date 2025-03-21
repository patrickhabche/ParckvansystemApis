package com.example.ParckvanSystemBackend.Entities;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="notes")
public class Note  {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String customerName;
    private LocalDateTime checkin;
    private LocalDateTime checkout;
    private int numberOfPersons;
    private float amount;
    private String phone;

    public Note() {
    }

    public boolean confirmed;

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    private boolean isDeleted;
    private LocalDateTime deletionTime;
    private Integer deleterUserId;
    private LocalDateTime lastModificationTime;
    private Integer lastModifierUserId;
    private int creatorUserId;
    private LocalDateTime creationTime;

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

    public void setDeleterUserId(Integer deleterUserId) {
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

    public void setLastModifierUserId(Integer lastModifierUserId) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(int numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
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

    public Note(String customerName, LocalDateTime checkin, LocalDateTime checkout, int numberOfPersons, float amount, String phone) {
        this.customerName = customerName;
        this.checkin = checkin;
        this.checkout = checkout;
        this.numberOfPersons = numberOfPersons;
        this.amount = amount;
        this.phone = phone;
    }
}
