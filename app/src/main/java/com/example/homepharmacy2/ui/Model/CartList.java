//IT19213972-----------I.K.S.S.Nawarathne
package com.example.homepharmacy2.ui.Model;

public class CartList {

    private String quantity;
    private  String address, customerName, foodName, nic, orderId, totalPrice , foodPrice , foodID;

    public CartList() {
    }

    public CartList(String address, String customerName, String foodName, String nic, String orderId, String totalPrice , String foodPrice , String quantity) {
        this.address = address;
        this.customerName = customerName;
        this.foodName = foodName;
        this.nic = nic;
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.foodPrice = foodPrice;
        this.quantity = quantity;
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

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
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

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getFoodID() {
        return foodID;
    }

    public void setFoodID(String foodID) {
        this.foodID = foodID;
    }
}