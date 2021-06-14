package com.bekdaulet.blog_project.exceptions;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(String s){
        super(s);
    }
}
