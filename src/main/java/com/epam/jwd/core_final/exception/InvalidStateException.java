package com.epam.jwd.core_final.exception;

public class InvalidStateException extends Exception {
    // todo
    public InvalidStateException(){}

    public InvalidStateException(Exception e){
        super(e);
    }

    public InvalidStateException(String message){
        super(message);
    }
    public  InvalidStateException (String message, Exception e){
        super(message, e);
    }

}
