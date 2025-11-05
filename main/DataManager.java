package main;

import java.io.*;
import java.util.List;
import java.util.HashMap;


public class DataManager {
    public static final String SLANG_TXT = "data/slang.txt";
    public static final String SLANG_DAT = "data/slang_map.dat";
    public static final String INDEX_DAT = "data/index.dat";
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
    private static HashMap<String, List<String>> loadSlangFromText() throws Exception {
        HashMap<String, List<String>> slangMap = new HashMap<>();
        BufferedReader fr = new BufferedReader(new FileReader(SLANG_TXT));
        String line;
        while ((line = fr.readLine()) != null) {
            String[] parts = line.split("`");
            if (parts.length != 2) 
                continue;
            String slang = parts[0].trim();
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
    private static void saveSlangDat(HashMap<String, List<String>> slangMap) throws Exception {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SLANG_DAT))) {
            oos.writeObject(slangMap); 
            System.out.println("Hashmap was saved!");
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
