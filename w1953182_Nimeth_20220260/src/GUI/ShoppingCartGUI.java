package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.Map;
import console.*;

// GUI class for displaying and managing the shopping cart
public class ShoppingCartGUI extends JFrame {
    private DefaultTableModel model2;        // Table model for the shopping cart table
    private JTable shoppingCartTable;        // Table to display items in the shopping cart
    private JLabel totalPriceLabel;          // Label to display the total price
    private Map<Product, Integer> cartItems; // Map to store items in the shopping cart

    // Constructor to set up the shopping cart GUI
    public ShoppingCartGUI() {
        setTitle("Shopping Cart");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(null);

        cartItems = new HashMap<>();

        // Table model and table for displaying items in the shopping cart
        model2 = new DefaultTableModel();
        model2.addColumn("Product");
        model2.addColumn("Quantity");
        model2.addColumn("Price");

        shoppingCartTable = new JTable(model2);
        JScrollPane scrollPane2 = new JScrollPane(shoppingCartTable);
        add(scrollPane2);
        scrollPane2.setBounds(50, 50, 700, 200);

        // Label to display the total price
        totalPriceLabel = new JLabel("Total Price: £0.00");
        add(totalPriceLabel);
        totalPriceLabel.setBounds(250, 300, 600, 100);
    }

    // Method to add a product to the shopping cart
    public void addToCart(Product product) {
        int quantity = 1;
        double price = product.getPrice() * quantity;

        // Check if the product is already in the cart
        if (cartItems.containsKey(product)) {
            int existingQuantity = cartItems.get(product);
            cartItems.put(product, existingQuantity + quantity);
            price += (product.getPrice() * existingQuantity);
            updateExistingRow(product, existingQuantity + quantity, price);
        } else {
            cartItems.put(product, quantity);
            addNewRow(product, quantity, price);
        }

        updateTotalPrice();
    }

    // Method to update the total price label
    private void updateTotalPrice() {
        double totalPrice = calculateTotalPrice();
        double discount = calculateDiscount(totalPrice);
        double finalTotalPrice = calculateFinalTotalPrice(totalPrice, discount);

        // Update the total price label with HTML formatting
        totalPriceLabel.setText("<html>Total Price&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: £" +
                String.format("%.2f", totalPrice) +
                "<br><br>Same category 3 items discount 20% :-£" + String.format("%.2f", discount) +
                "<br><br>Final Total Price&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: £" +
                String.format("%.2f", finalTotalPrice) + "</html>");
    }

    // Method to calculate the total price of items in the cart
    private double calculateTotalPrice() {
        double totalPrice = 0;

        for (int i = 0; i < model2.getRowCount(); i++) {
            totalPrice += (double) model2.getValueAt(i, 2);
        }

        return totalPrice;
    }

    // Method to calculate the discount based on the quantity of items in the same category
    private double calculateDiscount(double totalPrice) {
        // Apply a 20% discount if there are more than 3 items of the same category
        Map<String, Integer> categoryCount = new HashMap<>();
        for (Product product : cartItems.keySet()) {
            String category = getCategory(product);
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + cartItems.get(product));
        }

        for (int count : categoryCount.values()) {
            if (count > 3) {
                return totalPrice * 0.2; // 20% discount
            }
        }

        return 0;
    }

    // Method to calculate the final total price after applying the discount
    private double calculateFinalTotalPrice(double totalPrice, double discount) {
        return totalPrice - discount;
    }

    // Method to get the category of a product
    private String getCategory(Product product) {
        if (product instanceof Electronics) {
            return "Electronic";
        } else if (product instanceof Clothing) {
            return "Clothing";
        } else {
            return "";
        }
    }

    // Method to update an existing row in the shopping cart table
    private void updateExistingRow(Product product, int quantity, double price) {
        for (int i = 0; i < model2.getRowCount(); i++) {
            if (model2.getValueAt(i, 0).toString().startsWith(product.getProductName())) {
                model2.setValueAt(quantity, i, 1);
                model2.setValueAt(price, i, 2);
                break;
            }
        }
    }

    // Method to add a new row to the shopping cart table
    private void addNewRow(Product product, int quantity, double price) {
        String productInfo = getProductInfo(product); // Get the additional information
        model2.addRow(new Object[]{
                product.getProductName() + " (" + product.getProductId() + ", " + productInfo + ")",
                quantity,
                price
        });
    }

    // Method to get additional information about a product
    private String getProductInfo(Product product) {
        if (product instanceof Electronics) {
            Electronics electronics = (Electronics) product;
            return "Brand: " + electronics.getBrand() + ", Warranty: " + electronics.getWarrantyPeriod() + " years";
        } else if (product instanceof Clothing) {
            Clothing clothing = (Clothing) product;
            return "Size: " + clothing.getSize() + " inches, Color: " + clothing.getColour();
        } else {
            return "";
        }
    }
}