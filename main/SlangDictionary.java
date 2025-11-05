package main;

import java.util.HashMap;
import java.util.List;

public class SlangDictionary {
    private HashMap<String, List<String>> slangMap;     // slang -> list definitions
    private HashMap<String, List<String>> definitionMap; // definition -> list slang
    private List<String> history;

    public SlangDictionary() throws Exception {
        this.slangMap = DataManager.loadSlang();
    }
    public List<String> get(String slang) {
        return slangMap.get(slang);
    }
}
