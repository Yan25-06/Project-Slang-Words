package main;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class Main{
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws Exception{
        SlangDictionary dictionary;
        try {
            dictionary = new SlangDictionary();
        } 
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        int choice;
        do {
            System.out.println("\n===== SLANG WORD =====");
            System.out.println("1. Search slang");
            System.out.println("2. Search by definition");
            System.out.println("3. Show history");
            System.out.println("4. Add slang");
            System.out.print("Choose: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> {
                    System.out.println("Enter a slang word to search:");
                    String slang = scanner.nextLine();
                    System.out.print("Definitions for '" + slang + "': " + dictionary.searchSlang(slang));
                }
                case 2 -> {
                    System.out.println("Enter a definition to search:");
                    String definition = scanner.nextLine();
                    System.out.print("Slang words for definition '" + definition + "': " + dictionary.searchDefinition(definition));
                }
                case 3 -> dictionary.showHistory();
                case 4 -> {
                    System.out.println("Enter a slang word to add:");
                    String slang = scanner.nextLine();
                    System.out.println("Enter a definition of the slang:");
                    String defination = scanner.nextLine();
                    dictionary.addSlang(slang, defination);
                }
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }   
}
