package console;

import GUI.*;
public abstract class Product implements Comparable <Product> {

    private String productName;
    private String productId;
    public double price;
    private int quantity;

    //Default Constructor
    public Product(){
        productName = "Unknown";

        productId = "Not Found";
        price = -1;
        quantity = -1;
    }
    //Parameterized Constructor
    public  Product(String productId,String productName,int quantity,double price){
        this.productId=productId;
        this.productName=productName;
        this.quantity=quantity;
        this.price=price;}

    //Getters
    public String getProductName() {
        return productName;
    }
    public String getProductId() {
        return productId;
    }
    public double getPrice() {
        return price;
    }
    public int getQuantity(){
        return quantity; }

    //Setters
    public void setProductName(String productName) { this.productName = productName; }
    public void setProductId(String productId) { this.productId = productId; }
    public void setPrice(double price) {
        this.price = price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    //Display all details of the product
    @Override
    public String toString(){
        return "\n" + "Product Name: " + productName +
                "\n" + "Product ID: " + productId +
                "\n" + "Price: " + price +
                "\n" + "Number of items left: " + quantity;
    }

    // Add compareTo method for sorting by product ID
    @Override
    public int compareTo(Product otherProduct) {
        return this.productId.compareTo(otherProduct.productId);
    }
}
