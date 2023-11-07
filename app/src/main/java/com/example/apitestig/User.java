package com.example.apitestig;


public class User {
    String username;
    String password;
    String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;

    }

//    validate the user class with the following rules with static methods in the user class with attributes username, password, email
//    Username should be at least 6 characters long
//    Password should be at least 8 characters long
//    Email should be a valid email address

    public static boolean validateUsername(String username) {
        if (username.length() >= 6) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validatePassword(String password) {
        if (password.length() >= 8) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateEmail(String email) {
        if (email.contains("@")) {
            return true;
        } else {
            return false;
        }
    }

}

