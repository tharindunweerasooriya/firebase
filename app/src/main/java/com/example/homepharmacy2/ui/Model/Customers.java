//IT19213972-----------I.K.S.S.Nawarathne
package com.example.homepharmacy2.ui.Model;

public class Customers {
    private String customerName, customerPassword ,customerMobile, customerNic;

    public Customers() {
    }

    public Customers(String customerName, String customerPassword, String customerMobile, String customerNic) {
        this.customerName = customerName;
        this.customerPassword = customerPassword;
        this.customerMobile = customerMobile;
        this.customerNic = customerNic;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getCustomerNic() {
        return customerNic;
    }

    public void setCustomerNic(String customerNic) {
        this.customerNic = customerNic;
    }
}
