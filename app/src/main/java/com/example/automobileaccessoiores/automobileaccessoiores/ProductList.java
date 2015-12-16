package com.example.automobileaccessoiores.automobileaccessoiores;

import java.io.Serializable;

/**
 * Created by PC21 on 21/3/2015.
 */
public class ProductList implements Serializable {


    private String ProductImage;
    private String ProductName;
    private String phoneNumber;
    private String price;
    private String Showroomid;

    public String getShowroomid() {
        return Showroomid;
    }

    public void setShowroomid(String showroomid) {
        Showroomid = showroomid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ProductList() {

    }
    public ProductList(String productImage, String productName, String phoneNumber) {
        ProductImage = productImage;
        ProductName = productName;
        this.phoneNumber = phoneNumber;

    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
