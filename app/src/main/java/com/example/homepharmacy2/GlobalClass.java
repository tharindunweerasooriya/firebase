package com.example.homepharmacy2;

import android.app.Application;

public class GlobalClass extends Application {

    private String loggedRiderNIC;
    private String resetPasswordNIC, resetPasswordMobile;

    public String getLoggedRiderNIC() {
        return loggedRiderNIC;
    }

    public void setLoggedRiderNIC(String loggedRiderNIC) {
        this.loggedRiderNIC = loggedRiderNIC;
    }

    public String getResetPasswordNIC() {
        return resetPasswordNIC;
    }

    public void setResetPasswordNIC(String resetPasswordNIC) {
        this.resetPasswordNIC = resetPasswordNIC;
    }

    public String getResetPasswordMobile() {
        return resetPasswordMobile;
    }

    public void setResetPasswordMobile(String resetPasswordMobile) {
        this.resetPasswordMobile = resetPasswordMobile;
    }
}
