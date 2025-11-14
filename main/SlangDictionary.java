package main;

import java.util.ArrayList;
import java.util.Collections;
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
        List<String> mean = slangMap.get(slang.toUpperCase());
        DataManager.saveToHistory(slang + " -> " + slangMap.get(slang.toUpperCase()));
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
        slang = slang.toUpperCase();
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
    public void editSlang(String slang) {
        slang = slang.toUpperCase();
        if (!slangMap.containsKey(slang)) {
            System.out.println("Slang word not found.");
            return;
        }
        System.out.println("1. Edit Slang");
        System.out.println("2. Edit Definition");
        System.out.println("Choose: ");
        int choice;
        choice = Integer.parseInt(scanner.nextLine());
        if (choice == 1) {
            System.out.println("Enter new slang word: ");
            String newSlang = scanner.nextLine().toUpperCase();
            if (slangMap.containsKey(newSlang)) {
                System.out.println("Slang word already exists.");
                return;
            }
            List<String> definitions = slangMap.get(slang);
            slangMap.remove(slang);
            slangMap.put(newSlang, definitions);
            for (String def : definitions) {
                def = def.toLowerCase();
                List<String> slangs = definitionMap.get(def);
                slangs.remove(slang);
                slangs.add(newSlang);
                definitionMap.put(def, slangs);
            }
            System.out.println("Slang word edited.");
        } 
        else if (choice == 2) {
            List<String> definitions = slangMap.get(slang);
            for (int i = 0; i < definitions.size(); i++) {
                System.out.println((i + 1) + ". " + definitions.get(i));
            }
            System.out.println("Choose definition to edit: ");
            int defChoice = Integer.parseInt(scanner.nextLine());
            String newDef;
            String oldDef = definitions.get(defChoice - 1);
            while (true)
            {
                System.out.println("Enter new definition: ");
                newDef = scanner.nextLine().trim();
                if (definitionMap.containsKey(newDef.toLowerCase())) {
                    System.out.println("Definition existed, try again.");
                }
                else
                    break;
            }
            // Update slangMap
            List<String> slangs = definitionMap.get(oldDef.toLowerCase());
            System.out.print(slangs);
            for (String s : slangs) {
                List<String> defs = slangMap.get(s);
                System.out.println(defs);
                defs.remove(oldDef);
                defs.add(newDef);
                slangMap.put(s, defs);
            }
            // Update definitionMap
            List<String> slangsForNewDef = definitionMap.get(oldDef.toLowerCase());

            definitionMap.remove(oldDef);
            definitionMap.put(newDef, slangsForNewDef);
            System.out.println("Definition edited.");
            updateDefMap();
            updateSlangMap();
        } 
        else {
            System.out.println("Invalid choice.");
        }
    }
    public void deleteSlang(String slang) {
        slang = slang.toUpperCase();
        if (slangMap.containsKey(slang)) {
            System.out.println("Are you sure you want to delete this slang word? (Y/N)");
            String confirmation = scanner.nextLine();
            if (!confirmation.equalsIgnoreCase("Y")) {
                System.out.println("Deletion cancelled.");
                return;
            }
            List<String> definitions = slangMap.get(slang);
            for (String def : definitions) {
                def = def.toLowerCase();
                List<String> slangs = definitionMap.get(def);
                if (slangs != null) {
                    slangs.remove(slang);
                    if (slangs.isEmpty()) {
                        definitionMap.remove(def);
                    } 
                    else {
                        definitionMap.put(def, slangs);
                    }
                }
            }
            slangMap.remove(slang);
            System.out.println("Slang word deleted.");
            updateSlangMap();
            updateDefMap();
        } 
        else {
            System.out.println("Slang word not found.");
        }
    }
    public void resetDictionary() {
        try {
            slangMap = DataManager.loadSlangFromText();
            definitionMap = DataManager.loadDefFromText();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getRandomSlang() {
        List<String> keys = new ArrayList<>(slangMap.keySet());
        int randomIndex = (int) (Math.random() * keys.size());
        return keys.get(randomIndex);
    }
    public String getRandomDefinition() {
        List<String> keys = new ArrayList<>(definitionMap.keySet());
        int randomIndex = (int) (Math.random() * keys.size());
        return keys.get(randomIndex);
    }
    public void quizSlang() {
        String slang = getRandomSlang();
        List<String> correctDefs = slangMap.get(slang);
        String correctDef = correctDefs.get(0); 

        List<String> allDefinitions = new ArrayList<>();
        for (List<String> defs : slangMap.values()) {
            allDefinitions.addAll(defs);
        }

        allDefinitions.remove(correctDef);

        Collections.shuffle(allDefinitions);
        List<String> wrongAnswers = allDefinitions.subList(0, 3);

        List<String> options = new ArrayList<>();
        options.add(correctDef);
        options.addAll(wrongAnswers);

        Collections.shuffle(options);

        System.out.println("What is the definition of: " + slang + "?");
        for (int i = 0; i < 4; i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }

        System.out.print("Your answer: ");
        int choice = Integer.parseInt(scanner.nextLine());

        if (options.get(choice - 1).equals(correctDef)) {
            System.out.println("Correct!");
        } else {
            System.out.println("Wrong! The correct answer is: " + correctDef);
        }
    }
    public void quizDefinition() {
        String def = getRandomDefinition();
        List<String> correctSlangs = definitionMap.get(def);
        String correctSlang = correctSlangs.get(0); 

        List<String> allSlangs = new ArrayList<>();
        for (List<String> slangs : definitionMap.values()) {
            allSlangs.addAll(slangs);
        }
        allSlangs.remove(correctSlang);

        Collections.shuffle(allSlangs);
        List<String> wrongSlangs = allSlangs.subList(0, 3);

        List<String> options = new ArrayList<>();
        options.add(correctSlang);
        options.addAll(wrongSlangs);

        Collections.shuffle(options);

        System.out.println("Which slang matches this definition?");
        System.out.println("Definition: " + def);
        for (int i = 0; i < 4; i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }

        System.out.print("Your answer: ");
        int choice = Integer.parseInt(scanner.nextLine());

        if (options.get(choice - 1).equals(correctSlang)) {
            System.out.println("Correct!");
        } else {
            System.out.println("Wrong! The correct answer is: " + correctSlang);
        }
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
