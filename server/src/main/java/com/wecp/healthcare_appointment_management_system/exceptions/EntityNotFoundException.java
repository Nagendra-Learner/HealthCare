package com.wecp.healthcare_appointment_management_system.exceptions;

public class EntityNotFoundException extends RuntimeException
{
    public EntityNotFoundException()
    {}
    
    public EntityNotFoundException(String message)
    {
        super(message);
    }
}
