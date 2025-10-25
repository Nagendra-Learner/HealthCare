package com.wecp.healthcare_appointment_management_system.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity
public class Receptionist extends User {
    // implement receptionist entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    //mentioned to add some related/optional fields so that
    private String gender;

    public Receptionist() {
    }

    public Receptionist(User user, String gender) {
        this.user = user;
        this.gender = gender;
    }

    public Receptionist(Long id, User user, String gender) {
        this.id = id;
        this.user = user;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    


}