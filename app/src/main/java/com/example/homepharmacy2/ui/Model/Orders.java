//IT19213972-----------I.K.S.S.Nawarathne
package com.example.homepharmacy2.ui.Model;

public class Orders {

    private String address , customerName , foodName , mobileNumber , orderId , totalPrice;

    public Orders(String address, String customerName, String foodName, String mobileNumber, String orderId, String totalPrice) {
        this.address = address;
        this.customerName = customerName;
        this.foodName = foodName;
        this.mobileNumber = mobileNumber;
        this.orderId = orderId;
        this.totalPrice = totalPrice;
    }

    public Orders() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
