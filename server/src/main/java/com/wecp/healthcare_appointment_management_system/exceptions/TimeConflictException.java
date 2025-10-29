package com.wecp.healthcare_appointment_management_system.exceptions;

public class TimeConflictException extends RuntimeException
{

    public TimeConflictException()
    {

    }
    
    public TimeConflictException(String message)
    {
        super(message);
    }
}
