package main;

import javax.swing.*;
import java.awt.*;


public class Main {
    public static void main(String[] args) {
        SlangDictionary dictionary;
        try {
            dictionary = new SlangDictionary();
        } 
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(dictionary.get("BRB"));
    }   
}
