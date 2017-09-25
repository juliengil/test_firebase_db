package com.fifty_five.firebasedb;

/**
 * Created by Julien Gil on 22/09/2017.
 */

public class Product {

    private Long sku;
    private String name;
    private Double price;
    public  String id;

    public Product() {

    }

    public Product(String name, Long sku, Double price) {
        this.sku = sku;
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Long getSku() {
        return sku;
    }

    public Double getPrice() {
        return price;
    }
}
