package console;

import java.util.ArrayList;
import GUI.*;

public interface ShoppingManager {

    //Adds a new Product to the System
    void addProduct(ArrayList <Product> productList);

    //Removes existing Products from the System
    void removeProduct(ArrayList <Product> productList);

    //Displays a list of existing products of the System
    void printProductList(ArrayList <Product> productList);

    //Saves a list of existing products of the System onto a text file
    void saveFile(ArrayList <Product> productList);

    //Reads the list of existing products of the System from the text file
    void readFromFile(ArrayList <Product> productList);
}
