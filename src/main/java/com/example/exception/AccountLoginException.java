package com.example.exception;

public class AccountLoginException extends Exception{
    public AccountLoginException(String message){
        super(message);
    }
}