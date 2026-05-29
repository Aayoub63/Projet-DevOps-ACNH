package com.artheriom.acnhcollector.api.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String errorMessage){
        super(errorMessage);
    }
}

