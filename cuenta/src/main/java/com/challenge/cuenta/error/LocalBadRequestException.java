package com.challenge.cuenta.error;

public class LocalBadRequestException extends RuntimeException{
    public LocalBadRequestException(String message){
        super(message);
    }
}
