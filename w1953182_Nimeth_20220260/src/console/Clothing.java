package console;

import GUI.*;
public class Clothing extends Product {

    public String size;
    private String colour;

    //Constructors------------------------------------------------------------------------------------------------------
    //Default------------------------
    public Clothing() {
        size = "Unspecified";
        colour = "Unspecified";
    }
    //Parameterized------------------
    public Clothing(String productId,String productName,int availableStocks,
                    double price,String size,String colour){
        super(productId,productName,availableStocks,price);
        this.size=size;
        this.colour=colour;}

    //Getters-----------------------------------------------------------------------------------------------------------
    public String getSize() { return size; }
    public String getColour() { return colour; }

    //Setters-----------------------------------------------------------------------------------------------------------
    public void setSize(String size){ this.size = size; }
    public void setColour(String colour){ this.colour = colour; }

    //Display all details of the product--------------------------------------------------------------------------------
    @Override
    public String toString(){
        return super.toString() +
                "\nType: Clothing" +
                "\n" + "Size: " + size +
                "\n" + "Colour: " + colour +
                "\n ";
    }
}
