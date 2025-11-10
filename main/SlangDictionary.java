package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class SlangDictionary {
    private static final Scanner scanner = new Scanner(System.in);
    private HashMap<String, List<String>> slangMap;     // slang -> list definitions
    private HashMap<String, List<String>> definitionMap; // definition -> list slang

    public SlangDictionary() throws Exception {
        this.slangMap = DataManager.loadSlang();
        this.definitionMap = DataManager.loadDef();
    }
    public List<String> searchSlang(String slang) throws Exception {
        List<String> mean = slangMap.get(slang.toLowerCase());
        DataManager.saveToHistory(slang + " -> " + slangMap.get(slang.toLowerCase()));
        return mean;
    }
    public List<String> searchDefinition(String definition) throws Exception {
        List<String> mean = definitionMap.get(definition.toLowerCase());
        DataManager.saveToHistory(definition + " -> " + definitionMap.get(definition.toLowerCase()));
        return mean;
    }
    public void showHistory() throws Exception {
        System.out.println("======== HISTORY ========");
        List<String> history = DataManager.loadHistory();
        for (String slang : history) {
            System.out.println(slang);
        }
    }
    public void addSlang(String slang, String definations) {
        List<String> newDefs = new ArrayList<>();
        newDefs.add(definations);
        List<String> newSlangs = new ArrayList<>();
        newSlangs.add(slang);
        if (slangMap.containsKey(slang)) {
            System.out.println("Slang word already exists.");
            System.out.println("1. Overwrite");
            System.out.println("2. Duplicate");
            System.out.println("Choose: ");

            int choice;
            choice = Integer.parseInt(scanner.nextLine());
            if (choice == 1) {
                slangMap.put(slang, newDefs);
                definitionMap.put(definations, newSlangs);
                System.out.println("Slang word overwritten.");
            }
            else if (choice == 2) {
                List<String> existingDefs = slangMap.get(slang);
                if (existingDefs.contains(definations)) {
                    System.out.println("Definition already exists for this slang word.");
                    return;
                }
                existingDefs.add(definations);
                slangMap.put(slang, existingDefs);
                definitionMap.put(definations, newSlangs);
                System.out.println("Slang word duplicated.");
            } 
            else {
                System.out.println("Invalid choice.");
            }
        }
        else {
            slangMap.put(slang, newDefs);
            definitionMap.put(definations, newSlangs);
            System.out.println("Slang word added.");
        }
        updateSlangMap();
        updateDefMap();
    }
    private void updateSlangMap() {
        try {
            DataManager.saveSlangDat(slangMap);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updateDefMap() {
        try {
            DataManager.saveDefDat(definitionMap);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
