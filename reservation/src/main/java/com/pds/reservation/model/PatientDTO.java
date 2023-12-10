package com.pds.reservation.model;

import jakarta.validation.constraints.Size;


public class PatientDTO {

    private Long id;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String lastName;

    @Size(max = 255)
    private String mail;

    @Size(max = 255)
    private String telephone;

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(final String mail) {
        this.mail = mail;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(final String telephone) {
        this.telephone = telephone;
    }

}
