package com.example.qamberhaider.sales.Model;

/**
 * Created by qamber.haider on 8/6/2018.
 */

public class ShopDetails {

    public String shop_Title;
    public String shop_Address;
    public String shop_Latitude;
    public String shop_Longitude;
    public String shop_Code;
    public String id;

    public ShopDetails() {
    }

    public ShopDetails(String shop_Title, String shop_Address, String shop_Latitude, String shop_Longitude, String shop_Code, String id) {
        this.shop_Title = shop_Title;
        this.shop_Address = shop_Address;
        this.shop_Latitude = shop_Latitude;
        this.shop_Longitude = shop_Longitude;
        this.shop_Code = shop_Code;
        this.id = id;
    }

    public String getShop_Title() {
        return shop_Title;
    }

    public void setShop_Title(String shop_Title) {
        this.shop_Title = shop_Title;
    }

    public String getShop_Address() {
        return shop_Address;
    }

    public void setShop_Address(String shop_Address) {
        this.shop_Address = shop_Address;
    }

    public String getShop_Latitude() {
        return shop_Latitude;
    }

    public void setShop_Latitude(String shop_Latitude) {
        this.shop_Latitude = shop_Latitude;
    }

    public String getShop_Longitude() {
        return shop_Longitude;
    }

    public void setShop_Longitude(String shop_Longitude) {
        this.shop_Longitude = shop_Longitude;
    }

    public String getShop_Code() {
        return shop_Code;
    }

    public void setShop_Code(String shop_Code) {
        this.shop_Code = shop_Code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
