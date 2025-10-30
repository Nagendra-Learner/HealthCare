package com.wecp.healthcare_appointment_management_system.entity;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Receptionist extends User 
{   
    private String gender;
}