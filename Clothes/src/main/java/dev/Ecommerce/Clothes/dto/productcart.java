package dev.Ecommerce.Clothes.dto;

public class productcart {

    private int orderID;
    private int pID;
    private String name;
    private String price;
    private int quantity;

    public productcart(int orderID, int pID, String name, String price, int quantity) {
        this.orderID = orderID;
        this.pID = pID;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public productcart() {

    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getpID() {
        return pID;
    }

    public void setpID(int pID) {
        this.pID = pID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
