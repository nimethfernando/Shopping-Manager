package GUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import console.*;
import java.util.List;

// Main GUI class for the Westminster Shopping Centre
public class WestminsterShoppingCentreGUI extends JFrame {
    private DefaultTableModel model1;           // Table model for the main product table
    private JTextArea detailsTextArea;          // Text area to display details of selected product
    private List<Product> allProducts;          // Store all products read from the file
    private JComboBox<String> comboBox;         // Combo box for selecting product category
    private JTable mainProductTable;            // Table to display products
    private ShoppingCartGUI shoppingCartGUI;    // Shopping cart GUI

    // Constructor to set up the main GUI
    public WestminsterShoppingCentreGUI() {
        setTitle("Westminster Shopping Centre");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLayout(null);

        // Button to open the shopping cart
        JButton cartButton = new JButton("Shopping Cart");
        add(cartButton);
        cartButton.setBounds(820, 20, 150, 30);

        // ActionListener for the shopping cart button
        cartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openShoppingCart();
            }
        });

        // Combo box for selecting product category
        String[] comboBoxTypes = {"All", "Electronic", "Clothing"};
        comboBox = new JComboBox<>(comboBoxTypes);
        add(comboBox);
        comboBox.setBounds(350, 20, 300, 30);

        // ActionListener for the combo box
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) comboBox.getSelectedItem();
                updateTable(selectedCategory);
            }
        });

        // Label for the combo box
        JLabel comboBoxLabel = new JLabel("Select Product Category:");
        add(comboBoxLabel);
        comboBoxLabel.setBounds(180, 20, 200, 30);

        // Table model and table for displaying products
        model1 = new DefaultTableModel();
        model1.addColumn("Product ID");
        model1.addColumn("Name");
        model1.addColumn("Category");
        model1.addColumn("Price");
        model1.addColumn("Information");

        mainProductTable = new JTable(model1);
        JScrollPane scrollPane1 = new JScrollPane(mainProductTable);
        add(scrollPane1);
        scrollPane1.setBounds(50, 110, 900, 200);

        // ListSelectionListener for the main product table
        mainProductTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    displaySelectedRowDetails(mainProductTable.getSelectedRow());
                }
            }
        });

        // Button for adding selected product to the shopping cart
        JButton button2 = new JButton("Add to Cart");
        add(button2);
        button2.setBounds(420, 530, 150, 30);

        // ActionListener for the "Add to Cart" button
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToCart();
            }
        });

        // Text area for displaying details of the selected product
        detailsTextArea = new JTextArea();
        add(detailsTextArea);
        detailsTextArea.setBounds(50, 330, 900, 100);

        // Load product data from file and update the table
        allProducts = loadProductData();
        updateTable("All");

        // Initialize the shopping cart GUI
        shoppingCartGUI = new ShoppingCartGUI();
        setVisible(true);
    }

    // Method to add the selected product to the shopping cart
    private void addToCart() {
        int selectedRowIndex = mainProductTable.getSelectedRow();

        if (selectedRowIndex >= 0) {
            Product selectedProduct = allProducts.get(selectedRowIndex);
            shoppingCartGUI.addToCart(selectedProduct);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product to add to the shopping cart.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to open the shopping cart GUI
    private void openShoppingCart() {
        shoppingCartGUI.setVisible(true);
        shoppingCartGUI.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    // Method to load product data from the file
    private List<Product> loadProductData() {
        List<Product> productsForGUI = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("ItemList.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] productData = line.split(" ");
                String productType = productData[0];
                String id = productData[1];
                String name = productData[2];
                int stocks = Integer.parseInt(productData[3]);
                double price = Double.parseDouble(productData[4]);

                // Assuming product type is the first token
                if (productType.contains("Electronic")) {
                    String brand = productData[5];
                    float warrantyPeriod = Float.parseFloat(productData[6]);
                    productsForGUI.add(new Electronics(id, name, stocks, price, brand, warrantyPeriod));
                } else if (productType.contains("Clothing")) {
                    String size = productData[5];
                    String color = productData[6];
                    productsForGUI.add(new Clothing(id, name, stocks, price, size, color));
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace(); // Handle the exception appropriately in your application
        }
        return productsForGUI;
    }

    // Method to update the product table based on the selected category
    private void updateTable(String selectedCategory) {
        model1.setRowCount(0); // Clear existing rows
        for (Product product : allProducts) {
            if ("All".equals(selectedCategory) || isMatchingCategory(product, selectedCategory)) {
                Object[] rowData = getProductRowData(product);
                model1.addRow(rowData);
            }
        }
    }

    // Method to check if a product matches the selected category
    private boolean isMatchingCategory(Product product, String selectedCategory) {
        if ("Electronic".equals(selectedCategory) && product instanceof Electronics) {
            return true;
        } else return "Clothing".equals(selectedCategory) && product instanceof Clothing;
    }

    // Method to get row data for a product
    private Object[] getProductRowData(Product product3) {
        if (product3 instanceof Electronics electronics) {
            return new Object[]{electronics.getProductId(), electronics.getProductName(), "Electronic", electronics.getPrice(), "Brand: " + electronics.getBrand() + ", Warranty: " + electronics.getWarrantyPeriod() + " Years"};
        } else if (product3 instanceof Clothing clothing) {
            return new Object[]{clothing.getProductId(), clothing.getProductName(), "Clothing", clothing.getPrice(), "Size: " + clothing.getSize() + " inches, Color: " + clothing.getColour()};
        }
        return new Object[0];
    }

    // Method to display details of the selected row in the text area
    private void displaySelectedRowDetails(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < model1.getRowCount()) {
            StringBuilder detailsDisplay = new StringBuilder();
            for (int colIndex = 0; colIndex < model1.getColumnCount(); colIndex++) {
                detailsDisplay.append(model1.getColumnName(colIndex))
                        .append(": ")
                        .append(model1.getValueAt(rowIndex, colIndex))
                        .append("\n");
            }
            detailsTextArea.setText(detailsDisplay.toString());
        }
    }
}