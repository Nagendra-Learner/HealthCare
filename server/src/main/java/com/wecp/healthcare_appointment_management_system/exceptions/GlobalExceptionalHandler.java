package com.wecp.healthcare_appointment_management_system.exceptions;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionalHandler 
{
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException enfEx)
    {
        return new ResponseEntity<>(enfEx.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TimeConflictException.class)
    public ResponseEntity<String> handleTimeConflictException(TimeConflictException tcsEx)
    {
        return new ResponseEntity<>(tcsEx.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<String> handleDuplicateEntityException(DuplicateEntityException deEx)
    {
        return new ResponseEntity<>(deEx.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleResponseStatusException(ResponseStatusException rstEx)
    {
        Map<String,String> errorResponse = new HashMap<>();
        errorResponse.put("message", rstEx.getReason());
        errorResponse.put("status", String.valueOf(rstEx.getStatus().value()));
        return new ResponseEntity<>(errorResponse, rstEx.getStatus());
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleSQLException(SQLException sqlEx)
    {
        return new ResponseEntity<>(sqlEx.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
