package com.pds.reservation.model;

import java.time.LocalDateTime;


public class ReservationDTO {

    private Long id;
    private LocalDateTime date;
    private String description;
    private Long patient;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(final LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Long getPatient() {
        return patient;
    }

    public void setPatient(final Long patient) {
        this.patient = patient;
    }

}
