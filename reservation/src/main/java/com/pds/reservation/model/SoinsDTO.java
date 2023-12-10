package com.pds.reservation.model;

import jakarta.validation.constraints.Size;


public class SoinsDTO {

    private Long id;

    @Size(max = 255)
    private String name;

    private Double price;

    private String description;

    private Long reservationID;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Long getReservationID() {
        return reservationID;
    }

    public void setReservationID(final Long reservationID) {
        this.reservationID = reservationID;
    }

}
