package com.wecp.healthcare_appointment_management_system.exceptions;

public class DuplicateEntityException extends RuntimeException
{
    public DuplicateEntityException()
    {}

    public DuplicateEntityException(String message)
    {
        super(message);
    }
}
