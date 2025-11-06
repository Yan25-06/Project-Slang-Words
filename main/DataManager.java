package main;

import java.io.*;
import java.util.*;


public class DataManager {
    public static final String SLANG_TXT = "data/slang.txt";
    public static final String SLANG_DAT = "data/slang_map.dat";
    public static final String DEFINATION_DAT = "data/defination_map.dat";
    public static final String HISTORY_TXT = "data/history.txt";

    @SuppressWarnings("unchecked")
    public static HashMap<String, List<String>> loadSlang() throws Exception {
        File dat = new File(SLANG_DAT);
        if (dat.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dat))) {
                return (HashMap<String, List<String>>) ois.readObject();
            } 
            catch (Exception e) {
                System.out.println("Failed to read slang_map.dat, will rebuild from slang.txt");
            }
        }
        return loadSlangFromText();
    }
    @SuppressWarnings("unchecked")
    public static HashMap<String, List<String>> loadDef() throws Exception {
        File dat = new File(DEFINATION_DAT);
        if (dat.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dat))) {
                return (HashMap<String, List<String>>) ois.readObject();
            } 
            catch (Exception e) {
                System.out.println("Failed to read defination_map.dat, will rebuild from slang.txt");
            }
        }
        return loadDefFromText();
    }
    public static void saveToHistory(String slang) throws Exception {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(HISTORY_TXT, true))) {
            bw.write(slang);
            bw.newLine();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<String> loadHistory() throws Exception {
        List<String> history = new ArrayList<>();
        BufferedReader fr = new BufferedReader(new FileReader(HISTORY_TXT));
        String line;
        while ((line = fr.readLine()) != null) {
            history.add(line.trim());
        }
        fr.close();
        return history;
    }
    private static HashMap<String, List<String>> loadSlangFromText() throws Exception {
        HashMap<String, List<String>> slangMap = new HashMap<>();
        BufferedReader fr = new BufferedReader(new FileReader(SLANG_TXT));
        String line;
        while ((line = fr.readLine()) != null) {
            String[] parts = line.split("`");
            if (parts.length != 2) 
                continue;
            String slang = parts[0].trim().toLowerCase(Locale.ROOT);
            String[] definitions = parts[1].split("\\|");
            for (int i = 0; i < definitions.length; i++) {
                definitions[i] = definitions[i].trim();
            }
            slangMap.put(slang, List.of(definitions));
        }
        fr.close();
        saveSlangDat(slangMap);
        return slangMap;
    }
    private static HashMap<String, List<String>> loadDefFromText() throws Exception {
        HashMap<String, List<String>> defMap = new HashMap<>();
        BufferedReader fr = new BufferedReader(new FileReader(SLANG_TXT));
        String line;
        while ((line = fr.readLine()) != null) {
            String[] parts = line.split("`");
            if (parts.length != 2) 
                continue;
            String slang = parts[0].trim();
            String[] definitions = parts[1].split("\\|");
            for (String definition : definitions) {
                definition = definition.trim().toLowerCase(Locale.ROOT);
                defMap.putIfAbsent(definition, new ArrayList<>());
                defMap.get(definition).add(slang);
            }
        }
        fr.close();
        saveDefDat(defMap);
        return defMap;
    }
    private static void saveSlangDat(HashMap<String, List<String>> slangMap) throws Exception {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SLANG_DAT))) {
            oos.writeObject(slangMap); 
            System.out.println("Slang hashmap was saved!");
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void saveDefDat(HashMap<String, List<String>> defMap) throws Exception {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DEFINATION_DAT))) {
            oos.writeObject(defMap); 
            System.out.println("Defination hashmap was saved!");
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
