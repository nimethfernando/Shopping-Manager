package console;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import GUI.*;

public class WestminsterShoppingManager implements ShoppingManager {

    public static void main(String[] args) {
        WestminsterShoppingManager newShoppingManager = new WestminsterShoppingManager();
        newShoppingManager.displayMenu(storeItems);

    }

    static ArrayList<Product> storeItems = new ArrayList<>();
    static File ListFile = new File("ItemList.txt");
    final ArrayList<User> users = new ArrayList<>(50);

    //Menu console containing the Manager Actions
    private void displayMenu(ArrayList<Product> productList) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            // Menu options to be displayed on the console
            System.out.println("""
                    <-------Welcome to Westminster Shopping Center------>
                    ----------------------- MENU ------------------------
                   
                     \tEnter 1 or 'A' to add a product.
                     \tEnter 2 or 'D' to delete a product.
                     \tEnter 3 or 'P' to print the list of the products.
                     \tEnter 4 or 'S' to save progress in a File.
                     \tEnter 5 or 'L' to Load from File.
                     \tEnter 6 or 'G' to Launch the GUI.
                     \tEnter 0 or 'X' to Exit.
                     
                     ----------------------------------------------------
                     """);
            System.out.print("Enter your option: "); // taking user input for the menu
            String options = scanner.nextLine();

            if(options.equals("1") || options.equalsIgnoreCase("A")){
                addProduct(productList);
            } else if (options.equals("2") || options.equalsIgnoreCase("D")) {
                removeProduct(productList);
            } else if (options.equals("3") || options.equalsIgnoreCase("P")) {
                printProductList(productList);
            } else if (options.equals("4") || options.equalsIgnoreCase("S")) {
                saveFile(productList);
            } else if (options.equals("5") || options.equalsIgnoreCase("L")) {
                readFromFile(productList);
            } else if (options.equals("6") || options.equalsIgnoreCase("G")) {
                LoginGUI loginGUI = new LoginGUI(users,productList);
                loginGUI.setTitle("Login or Register");
                loginGUI.setSize(500, 500);
                loginGUI.setResizable(false);
                loginGUI.setVisible(true);
            } else if (options.equals("0") || options.equalsIgnoreCase("X")) {
                System.out.println("Exiting the Program...");
                System.exit(0); //Ending loop to exit program.

            } else {   //Checking if an option from the menu was entered. If not program will loop back to take user input.
                System.out.println("Invalid Option!!!\n");
            }
        }
}

    //Adds a new Product to the System
    @Override
    public void addProduct(ArrayList<Product> productList) {

        if (productList.size() < 50) {
            while (true) {
                // calling createProduct method to chose to create an object of  electronic or clothing
                Product product = createProduct();
                if (product != null) {
                    productList.add(product);
                    System.out.println("You've successfully added the following Product");
                    //Checking which subclass the "product" object is an instance of
                    System.out.println(product);

                    if (stopActionMoreProducts("adding")) {
                        break;
                    }
                } else {
                    break;
                }
            }
        } else {
            System.out.println("""
                    The system can only contain 50 Products at most.
                    Try deleting some products first.
                    """);
        }
    }

    //Creates a Product to be added to the Store
    private Product createProduct(){
        Product product;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("""
                    
                    Enter the category of this Product.
                    (Enter "E" for Electronic, "C" for Clothing and "Q" to go back)
                    Type here:\s""");

            String category = scanner.next().toUpperCase();

            switch (category) {
                case "E" -> product = new Electronics();
                case "C" -> product = new Clothing();
                case "Q" -> {
                    return null;
                }
                default -> {
                    System.out.println("Invalid Input. Please try again.");
                    continue;
                }
            }
            // Calling setProductDetails to get the details of the product
            setProductDetails(product, storeItems, scanner);
            break;
        }

        return product;
    }

    //Sets Product attributes
    private void setProductDetails(Product product, ArrayList <Product> productList, Scanner scanner){
        //Checking for duplicate Product IDs
        product.setProductId(Validator.getValidProductId(scanner, productList, "\nEnter Product ID: "));

        System.out.print("Enter Product Name: ");
        product.setProductName(scanner.next().toUpperCase());

        //Validating Double Input
        product.setPrice(Validator.getValidDoubleInput(scanner,"Enter Price: "));

        //Validating Integer Input
        product.setQuantity(Validator.getValidIntInput(scanner, "Enter Number of available items: "));

        //Checking which subclass the "product" object is an instance of
        if (product instanceof Electronics) {
            setElectronicDetails(product, scanner);
        }
        else {
            setClothingDetails(product, scanner);
        }
    }
    // Set Electronic details
    private void setElectronicDetails(Product product, Scanner scanner) {
        System.out.print("Enter Brand Name: ");
        ((Electronics) product).setBrand(scanner.next().toUpperCase());
        //Setting warranty Period and Validating Input
        ((Electronics) product).setWarrantyPeriod(Validator.getValidFloatInput(scanner, "Enter Warranty(in years): "));
    }
    // Set Clothing details
    private void setClothingDetails(Product product, Scanner scanner) {
        //Validating Input
        while (true) {
            System.out.print("Enter Size(XS, S, M, L or XL): ");
            String sizeInput = scanner.next().toUpperCase();
            if (sizeInput.equals("XS") ||
                    sizeInput.equals("S") ||
                    sizeInput.equals("M") ||
                    sizeInput.equals("L") ||
                    sizeInput.equals("XL")) {
                ((Clothing) product).setSize(sizeInput);
                break;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }

        System.out.print("Enter Colour: ");
        ((Clothing) product).setColour(scanner.next());
    }


    //Removes existing Products from the System
    @Override
    public void removeProduct(ArrayList <Product> productList) {

        if (!productList.isEmpty()) {
            while (true) {
                System.out.print("""
                        
                        Enter the Product ID to remove the Product from the System.
                        (Enter "Q" to go back)
                        Type here:\s""");
                Scanner scanner = new Scanner(System.in);
                String productId = scanner.next().toUpperCase();

                //Go back to the menu
                if (productId.equals("Q")) {
                    break;
                }

                findAndRemove(productId, productList);

                if (stopActionMoreProducts("deleting")) {
                    break;
                }
            }
        }
        else {
            System.out.println("The System does not contain any Products at the moment.");
        }
    }

    //Find the Product from the array by Product ID
    private void findAndRemove(String productId, ArrayList <Product> productList) {
        Product productToDelete = null;

        for (Product product : productList) {
            if (product.getProductId().equals(productId.toUpperCase())) {
                productToDelete = product;
                productList.remove(product);
                break;
            }
        }
        if (productToDelete == null) {
            System.out.println("\nThere is no product with the product ID " + productId.toUpperCase() + " in the system." +
                    "\nPlease try again.");
        } else {
            System.out.println("You've successfully deleted the following Product\n");
            // To Print the details of the product (toString method will be called)
            System.out.println(productToDelete);
        }
    }

    // To continue with the same manu or go back to different manu
    private boolean stopActionMoreProducts(String Action){
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.printf("""
                    Do you wish to retain additional %s products?
                    (Please input "Y" for Yes or "N" for No)\s""", Action);
            String more = scanner.next().toUpperCase();

            switch (more) {
                case "Y" -> {
                    return false;
                }
                case "N" -> {
                    return true;
                }
                default -> System.out.println("Invalid Input. Please try again.");
            }
        }
    }

    //Displays a list of existing products of the System
    @Override
    public void printProductList(ArrayList<Product> productList) {
        if (!productList.isEmpty()){
            //To sort the list
            Collections.sort(productList);

            for(int i=0; i < productList.size(); i++){
                System.out.println((i+1)+ ")\n" + productList.get(i)+"\n");
            }
        }else {
            System.out.println("""
                    Currently, the system lacks any products.
                    Please attempt to add products before proceeding.
                    """);
        }
    }

    //Saves a list of existing products of the System onto a text file
    @Override
    public void saveFile(ArrayList <Product> productList) {
        if (!productList.isEmpty() || noProductsToSave()) {
            try {
                FileWriter fileWriter = new FileWriter(ListFile);
                PrintWriter printWriter = new PrintWriter(fileWriter);

                //To sort the list
                Collections.sort(productList);

                for (Product product : productList) {
                    printWriter.println(formatAllDetails(product));
                }

                printWriter.close();
                System.out.println("Saving to File was Successful.");

            } catch (IOException e) {
                System.out.println("A failure occurred during the saving process to the file.");
                e.printStackTrace();
            }
        }

    }

    //Arranging all Product details in an easy-to-read format in the text file
    // And to check whether it is Electronic or Clothing
    private String formatAllDetails(Product product) {
        if (product instanceof Electronics) {
            return "Electronic: " +
                    formatCommonDetails(product) +
                    ((Electronics) product).getBrand() + " " +
                    ((Electronics) product).getWarrantyPeriod();
        } else {
            return "Clothing: " +
                    formatCommonDetails(product) +
                    ((Clothing) product).getSize() + " " +
                    ((Clothing) product).getColour();
        }
    }
    //Arranging common Product details in an easy-to-read format in the text file
    private String formatCommonDetails(Product product) {
        return product.getProductId() + " " +
                product.getProductName() + " " +
                product.getQuantity() + " " +
                product.getPrice() + " ";
    }

    //Asking the user if they want to erase data or not
    private boolean noProductsToSave() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                   No new products are available for saving.
                   If you proceed, all previously saved products will be erased.
                    """);
        while (true) {
            System.out.print("""
                    Do you want to continue? (Enter "Y" for Yes, "N" for No)
                    Type here:\s""");
            String save = scanner.next().toUpperCase();

            switch (save) {
                case "Y" -> {
                    return true;
                }
                case "N" -> {
                    return false;
                }
                default -> System.out.println("Invalid Input. Please try again.\n");
            }
        }
    }

    //Reading the list of existing products of the System from the text file
    @Override
    public void readFromFile(ArrayList <Product> productList) {
        try {
            FileReader fileReader = new FileReader(ListFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            //Reading till the end of the text file (till there are no more lines)
            while ((line = bufferedReader.readLine()) != null) {
            //(line = bufferedReader.readLine() updates "line" inside the loop
                String[] parts = line.trim().split(" ");
                String type = parts[0];
                String productId = parts[1];
                String productName = parts[2];
                int quantity = Integer.parseInt(parts[3]);
                double price = Double.parseDouble(parts[4]);

                //Creating a Product object and adding the details
                Product product;

                if (type.equals("Electronic:")) {
                    String brand = parts[5];
                    float warranty = Float.parseFloat(parts[6]);
                    product = new Electronics(productId, productName, quantity, price, brand, warranty);
                } else {
                    String size = parts[5];
                    String color = parts[6];
                    product = new Clothing(productId, productName, quantity, price, size, color);
                }

                //Checking for duplicate Product IDs
                if (Validator.duplicateProductId(productId, productList)) {
                    System.out.println("The following product was not added.\n" + product +
                            "\nThe system already contains a product with the same Product ID\n");
                } else {
                    storeItems.add(product);
                }
            }
            System.out.println("Loading Products was Successful.\n");
        } catch (FileNotFoundException e) {
            System.out.println("Loading Products was Unsuccessful. Check if " + ListFile + " file exists.");
        } catch (IOException e) {
            System.out.println("""
                                There was an error encountered while attempting to read the file,
                                resulting in an unsuccessful attempt to load products.
                                """);
        }
    }
}
