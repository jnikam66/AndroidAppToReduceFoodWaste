package com.jyoti.NoFoodWastage;

public class User {

    public String userType;
    public String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.userType = username;
        this.email = email;
    }

}
