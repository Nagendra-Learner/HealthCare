package com.wecp.healthcare_appointment_management_system.entity;

import javax.persistence.Entity;

@Entity
public class Receptionist extends User 
{   
    private String gender;
    
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
}