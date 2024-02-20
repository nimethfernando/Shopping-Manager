package GUI;

import console.Product;

import java.util.ArrayList;

public class User {
    private final String username;
    private final String password;
    private final ArrayList<Product> purchaseHistory;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.purchaseHistory = new ArrayList<>();
    }

    // getter for username
    public String getUsername() {
        return username;
    }

    // getter for password
    public String getPassword() {
        return password;
    }

    public boolean firstPurchase(){
        return this.purchaseHistory.isEmpty();
    }

    public void addPurchase(Product purchase){
        purchaseHistory.add(purchase);
    }

}
