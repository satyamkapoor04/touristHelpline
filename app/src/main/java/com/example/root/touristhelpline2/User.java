package com.example.root.touristhelpline2;

public class User {
    private String username,state,gender,email;

    public User (String username, String gender, String email) {
        this.username = username;
        this.gender = gender;
        this.email = email;
    }

    public String getUsername () {
        return this.username;
    }

    public String getGender () {
        return this.gender;
    }

    public String getEmail () {
        return this.email;
    }
}
