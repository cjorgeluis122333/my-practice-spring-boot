package com.microservice.eureca.my_practice_springboot.model.exception;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {
        super("The user is not present in the database");
    }
}
