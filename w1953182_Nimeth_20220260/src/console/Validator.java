package console;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import GUI.*;

public class Validator {
    //Validators for Product creation
    //Validating for Double inputs
    public static double getValidDoubleInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextDouble();
            }
            catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Consume the invalid input
            }
        }
    }
    //Validating for Integer inputs
    public static int getValidIntInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextInt();
            }
            catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); // Consume the invalid input
            }
        }
    }
    //Validating for Float inputs
    public static float getValidFloatInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextFloat();
            }
            catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Consume the invalid input
            }
        }
    }
    //Validating for Product ID
    public static String getValidProductId(Scanner scanner, ArrayList <Product> arrayList, String prompt) {
        while (true) {
            System.out.print(prompt);
            String potentialId = scanner.next().toUpperCase();
            if (duplicateProductId(potentialId, arrayList)) {
                System.out.println("""
                        There's already a product with the Product ID you entered.
                        Please enter a different ID.
                        """);
            } else {
                return potentialId;
            }
        }
    }
    //Checking for existing Product IDs
    public static boolean duplicateProductId(String productId, ArrayList <Product> arrayList) {
        for (Product product : arrayList) {
            if (product.getProductId().equals(productId)) {
                return true;
            }
        }
        return false;
    }
}
