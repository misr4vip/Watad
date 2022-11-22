package com.example.watad;

public class AddressModel {

    private String userId;
    private String city;
    private String district;
    private String street;
    private String building;
    private String postelCode;

   public  AddressModel(){}
    public  AddressModel(String userId ,String city ,String district ,String street ,String building ,String postelCode ){
       this.userId = userId ;
       this.city = city;
       this.district = district;
       this.street = street;
       this.building = building;
       this.postelCode = postelCode;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuiding() {
        return building;
    }

    public void setBuiding(String buiding) {
        this.building = buiding;
    }

    public String getPostelCode() {
        return postelCode;
    }

    public void setPostelCode(String postelCode) {
        this.postelCode = postelCode;
    }
}
