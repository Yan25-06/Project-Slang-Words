package main;

import java.util.HashMap;
import java.util.List;

public class SlangDictionary {
    private HashMap<String, List<String>> slangMap;     // slang -> list definitions
    private HashMap<String, List<String>> definitionMap; // definition -> list slang
    private List<String> history;

    public SlangDictionary() throws Exception {
        this.slangMap = DataManager.loadSlang();
        this.definitionMap = DataManager.loadDef();
    }
    public List<String> searchSlang(String slang) {
        System.out.println("Searching for slang: " + slang.toLowerCase());
        return slangMap.get(slang.toLowerCase());
    }
    public List<String> searchDefinition(String definition) {
        return definitionMap.get(definition.toLowerCase());
    }
}
