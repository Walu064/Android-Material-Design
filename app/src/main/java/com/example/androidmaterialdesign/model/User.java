package com.example.androidmaterialdesign.model;

public class User {

    private final String userLogin;
    private final String userPassword;
    private final String userEmail;
    private final String userName;
    private final String userSurname;

    public User( String login, String password, String name, String surname, String email){
        this.userLogin = login;
        this.userPassword = password;
        this.userName = name;
        this.userSurname = surname;
        this.userEmail = email;
    }

    public String getUserLogin(){
        return this.userLogin;
    }

    public String getUserPassword(){
        return this.userPassword;
    }

    public String getUserName(){
        return this.userName;
    }

    public String getUserSurname(){
        return this.userSurname;
    }

    public String getUserEmail(){
        return this.userEmail;
    }

}
