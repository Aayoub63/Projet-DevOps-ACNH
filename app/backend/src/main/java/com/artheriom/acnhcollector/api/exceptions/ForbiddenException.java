package com.artheriom.acnhcollector.api.exceptions;

public class ForbiddenException extends RuntimeException{
    public ForbiddenException(String errorMessage){
        super(errorMessage);
    }
}
