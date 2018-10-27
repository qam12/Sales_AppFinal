package com.example.qamberhaider.sales.Model;

/**
 * Created by qamber.haider on 8/8/2018.
 */

public class ShopDetails_Retrive {

    public String title_Shop;
    public String address_Shop;
    public String code_Shop;

    public ShopDetails_Retrive() {
    }

    public ShopDetails_Retrive(String title_Shop, String address_Shop, String code_Shop) {
        this.title_Shop = title_Shop;
        this.address_Shop = address_Shop;
        this.code_Shop = code_Shop;
    }

    public String getTitle_Shop() {
        return title_Shop;
    }

    public void setTitle_Shop(String title_Shop) {
        this.title_Shop = title_Shop;
    }

    public String getAddress_Shop() {
        return address_Shop;
    }

    public void setAddress_Shop(String address_Shop) {
        this.address_Shop = address_Shop;
    }

    public String getCode_Shop() {
        return code_Shop;
    }

    public void setCode_Shop(String code_Shop) {
        this.code_Shop = code_Shop;
    }
}
