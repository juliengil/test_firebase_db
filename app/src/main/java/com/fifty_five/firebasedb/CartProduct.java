package com.fifty_five.firebasedb;

/**
 * Created by Julien Gil on 25/09/2017.
 */

public class CartProduct {

    public  String id;
    private Long sku;
    private String name;
    private Double price;
    private Long quantity;


    public CartProduct() {

    }

    public CartProduct(String name, Long sku, Double price, Long quantity) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public CartProduct(Product prod) {
        this.sku = prod.getSku();
        this.name = prod.getName();
        this.price = prod.getPrice();
        this.quantity = Long.parseLong("1");
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

    public void setQuantity(Long quantity) {
        this.quantity =  quantity;
    }

    public Long getQuantity() {
        return quantity;
    }
}
