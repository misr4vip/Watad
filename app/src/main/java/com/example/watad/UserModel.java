package com.example.watad;

public class UserModel {
    private String Id;
    private String Name;
    private String email;
    private String city;
    private String userType;
    private boolean isActive;
    public UserModel(){};


    public UserModel(String id , String name , String email,String city,String userType,boolean isActive)
    {
        this.email = email;
        this.Id = id;
        this.Name = name;
        this.city = city;
        this.userType = userType;
        this.isActive = isActive;
    }
    public String getId() {
        return Id;
    }

    public void setId(String id) {

        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
            Name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
