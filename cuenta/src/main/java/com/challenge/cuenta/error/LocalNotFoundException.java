package com.challenge.cuenta.error;

public class LocalNotFoundException extends RuntimeException {
    public LocalNotFoundException(String message){
        super(message);
    }
}