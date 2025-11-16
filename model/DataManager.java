package model;
import java.io.*;
import java.util.*;


public class DataManager {
    public static final String SLANG_TXT = "data/slang.txt";
    public static final String SLANG_DAT = "data/slang_map.dat";
    public static final String DEFINITION_DAT = "data/definition_map.dat";
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
        File dat = new File(DEFINITION_DAT);
        if (dat.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dat))) {
                return (HashMap<String, List<String>>) ois.readObject();
            } 
            catch (Exception e) {
                System.out.println("Failed to read definItion_map.dat, will rebuild from slang.txt");
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
        File historyFile = new File(HISTORY_TXT);
        if (historyFile.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(historyFile));
            String line;
            while ((line = br.readLine()) != null) {
                history.add(line.trim());
            }
            br.close();
        }
        return history;
    }
    public static HashMap<String, List<String>> loadSlangFromText() throws Exception {
        HashMap<String, List<String>> slangMap = new HashMap<>();
        BufferedReader fr = new BufferedReader(new FileReader(SLANG_TXT));
        String line;
        while ((line = fr.readLine()) != null) {
            String[] parts = line.split("`");
            if (parts.length != 2) 
                continue;
            String slang = parts[0].trim().toUpperCase(Locale.ROOT);
            String[] definitions = parts[1].split("\\|");
            List<String> defList = new ArrayList<>();
            for (int i = 0; i < definitions.length; i++) {
                definitions[i] = definitions[i].trim();
                defList.add(definitions[i]);
            }
            slangMap.put(slang, defList);
        }
        fr.close();
        saveSlangDat(slangMap);
        return slangMap;
    }
    public static HashMap<String, List<String>> loadDefFromText() throws Exception {
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
    public static void saveSlangDat(HashMap<String, List<String>> slangMap) throws Exception {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SLANG_DAT))) {
            oos.writeObject(slangMap); 
            System.out.println("Slang hashmap was saved!");
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveDefDat(HashMap<String, List<String>> defMap) throws Exception {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DEFINITION_DAT))) {
            oos.writeObject(defMap); 
            System.out.println("Definition hashmap was saved!");
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
