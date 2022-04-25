package com.example.mentorplus;

import java.util.ArrayList;
import java.util.List;

public class Dish {
    private String dish_name;
    private ArrayList<String> customisation;
    private String price;

    public Dish() {
    }

    public Dish(String dish_name, ArrayList<String> customisation, String price) {
        this.dish_name = dish_name;
        this.customisation = customisation;

        this.price = price;
    }

    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public List<String> getCustomisation() {
        return customisation;
    }

    public void setCustomisation(ArrayList<String> customisation) {
        this.customisation = customisation;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
