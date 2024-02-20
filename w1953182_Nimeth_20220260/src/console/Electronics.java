package console;

import GUI.*;
public class Electronics extends Product {

    private String brand;
    private float warrantyPeriod;

    //Default Constructor
    public Electronics() {
        brand = "Not Specified";
        warrantyPeriod = 0;
    }
    //Parameterized Constructor
    public Electronics(String productId, String productName, int quantity, double price,
                       String brand, float warrantyPeriod) {
        super(productId,productName,quantity,price);
        this.brand = brand;
        this.warrantyPeriod=warrantyPeriod;}

    //Getters
    public String getBrand() {
        return brand; }
    public float getWarrantyPeriod() {
        return warrantyPeriod; }

    //Setters
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public void setWarrantyPeriod(float warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    //Display all details of the product
    @Override
    public String toString(){
        return super.toString() +
                "\nType: Electronic" +
                "\n" + "Brand: " + brand +
                "\n" + "Warranty Period: " + warrantyPeriod + " years" +
                "\n";
    }
}
