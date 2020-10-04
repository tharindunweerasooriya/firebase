//IT19213972-----------I.K.S.S.Nawarathne
package com.example.homepharmacy2.ui.Model;

public class Food {

    private String foodName, foodDes,foodPrice,foodImage,foodID;

    public Food(String foodName, String foodDes, String foodPrice, String foodImage, String foodID) {
        this.foodName = foodName;
        this.foodDes = foodDes;
        this.foodPrice = foodPrice;
        this.foodImage = foodImage;
        this.foodID = foodID;

    }

    public Food() {
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodDes() {
        return foodDes;
    }

    public void setFoodDescription(String foodDes) {
        this.foodDes = foodDes;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public String getFoodID() {
        return foodID;
    }

    public void setFoodID(String foodID) {
        this.foodID = foodID;
    }

}

