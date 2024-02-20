package GUI;

import console.Product;

import java.util.ArrayList;
import java.util.Iterator;

public class ShoppingCart {
    private ArrayList<Product> cartProductList;                          //declaring the arraylist with argument passed products as an instance variable

    public ShoppingCart(){                                                     //setting the overloaded constructor
        this.cartProductList=new ArrayList<>();}

    public void addCartProduct(Product product){                        //method to add products to the shopping cart

        this.cartProductList.add(product);
                                                                           //printing the added product
        System.out.println("<<Added product>> \n"+
                            "Product Id:"+product.getProductId()+
                            "Product Name:"+product.getProductName()+
                            "Available stocks:"+product.getQuantity()+
                            "Price:"+product.getPrice());
    }


    public void removeCartProduct(Product product){                          //method to remove products from the shopping cart

        this.cartProductList.remove(product);
                                                                            //printing the removed product
        System.out.println("<<Removed Product>> \n"+
                            "Product Id:"+product.getProductId()+
                            "Product Name:"+product.getProductName()+
                            "Available stocks:"+product.getQuantity()+
                            "Price:"+product.getPrice());
    }


    public void calculateTotal(){                                           //method to calculate total price of products in the cart
        double totalPrice = 0;

        Iterator <Product> iterator = cartProductList.iterator();           // declaring iterator method
        while (iterator.hasNext()){                                         // while loop to go through each product
            Product product= iterator.next();
            totalPrice+= product .getPrice();}

        System.out.println(" *>Total Price:" + totalPrice);
    }

}
