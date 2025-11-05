package main;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;


public class Main {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        SlangDictionary dictionary;
        try {
            dictionary = new SlangDictionary();
        } 
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Enter a slang word to search:");
        String slang = scanner.nextLine();
        System.out.print("Definitions for '" + slang + "': " + dictionary.searchSlang(slang));

        // System.out.println("Enter a definition to search:");
        // String definition = scanner.nextLine();
        // System.out.print("Slang words for definition '" + definition + "': " + dictionary.searchDefinition(definition));
        scanner.close();
    }   
}
