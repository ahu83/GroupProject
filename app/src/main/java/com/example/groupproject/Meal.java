package com.example.groupproject;

public class Meal {
    private int id,  image;
    private String name;
    private int price;

    public Meal(int id, String name, int image, int price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return  "name: " + name +
                ", price: " + price ;
    }
}
