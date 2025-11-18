package model;
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
    public List<String> showHistory() throws Exception {
        return DataManager.loadHistory();
    }
    public int addSlang(String slang, String definations, List<String> newDefs, List<String> newSlangs) {
        if (slangMap.containsKey(slang)) {
            return 1;
        }
        else {
            slangMap.put(slang, newDefs);
            definitionMap.put(definations, newSlangs);
            return 2;
        }
    }
    public String doChoice(int choice, String slang, String defination, List<String> newDefs, List<String> newSlangs) {
        if (choice == 0) {
            slangMap.put(slang, newDefs);
            definitionMap.put(defination, newSlangs);
            return "OVERWRITTEN";
        }
        if (choice == 1) {
            List<String> existingDefs = slangMap.get(slang);
            if (existingDefs.contains(defination)) {
                return "EXISTED DEF";
            }
            existingDefs.add(defination);
            slangMap.put(slang, existingDefs);
            definitionMap.put(defination, newSlangs);
            return "DUPLICATED";
        } 
        return "INVALID";
    }
    public void updateMap() {
        updateSlangMap();
        updateDefMap();
    }
    public String editSlang(String slang) {
        slang = slang.toUpperCase();
        if (!slangMap.containsKey(slang)) {
            return "NOTFOUND";
        }
        return "";
    }
    public void editDefinition(String newDef, String oldDef) {
        // List<String> definitions = slangMap.get(slang);
        // String newDef;
        // String oldDef = definitions.get(defChoice - 1);
        // while (true)
        // {
        //     // log("Enter new definition: ");
        //     newDef = scanner.nextLine().trim();
        //     if (definitionMap.containsKey(newDef.toLowerCase())) {
        //         // log("Definition existed, try again.");
        //     }
        //     else
        //         break;
        // }
        // Update slangMap
        List<String> slangs = definitionMap.get(oldDef.toLowerCase());
        System.out.print(slangs);
        for (String s : slangs) {
            List<String> defs = slangMap.get(s);
            defs.remove(oldDef);
            defs.add(newDef);
            slangMap.put(s, defs);
        }
        // Update definitionMap
        List<String> slangsForNewDef = definitionMap.get(oldDef.toLowerCase());

        definitionMap.remove(oldDef);
        definitionMap.put(newDef, slangsForNewDef);
    }
    public String editSlangWord(String newSlang, String slang) {
        if (slangMap.containsKey(newSlang)) {
            return "EXISTED SLANG";
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
        return "EDITED SLANG";
    }
    public void deleteSlang(String slang) {
        slang = slang.toUpperCase();
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
        updateSlangMap();
        updateDefMap();
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
    public List<String> quizSlang(String slang) {
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

        return options;
    }
    public List<String> quizDefinition(String def) {
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

        return options;
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
