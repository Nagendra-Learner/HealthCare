package com.wecp.healthcare_appointment_management_system.exceptions;

public class InvalidAssociationException extends RuntimeException
{
    public InvalidAssociationException()
    {}

    public InvalidAssociationException(String message)
    {
        super(message);
    }
}
