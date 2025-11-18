package controller;

import model.SlangDictionary;
import view.MainMenuView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

public class MainMenuController {
    private SlangDictionary dictionary;
    private MainMenuView view;

    public MainMenuController(SlangDictionary dictionary, MainMenuView view) throws Exception{
        this.dictionary = dictionary;
        this.view = view;

        // ====== LỊCH SỬ ======
        view.groupHistory.addActionListener(e -> loadHistory());

        // ====== SEARCH PANEL ======
        view.panelSearch.btnSearchSlang.addActionListener(e -> searchSlang());
        view.panelSearch.btnSearchDefinition.addActionListener(e -> searchDefinition());

        // ====== MANAGE PANEL ======
        view.panelManage.btnAdd.addActionListener(e -> {
            try {
                addSlang();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
            }
        });
        view.panelManage.btnEdit.addActionListener(e -> {
            try {
                editSlang();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
            }
        });
        view.panelManage.btnDelete.addActionListener(e -> {
            try {
                deleteSlang();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
            }
        });
        view.panelManage.btnReset.addActionListener(e -> resetDictionary());

        // ====== RANDOM PANEL ======
        view.panelRandom.btnRandom.addActionListener(e -> {
            try {
                randomSlang();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
            }
        });

        // ====== QUIZ PANEL ======
        view.panelQuiz.btnQuizSlang.addActionListener(e -> quizSlang());
        view.panelQuiz.btnQuizDefinition.addActionListener(e -> quizDefinition());
    }

    private void searchSlang() {
        String slang = JOptionPane.showInputDialog(view, "Enter slang:");
        if (slang != null && !slang.trim().isEmpty()) {
            try {
                List<String> defs = dictionary.searchSlang(slang);
                view.panelSearch.resultModel.clear();
                if (defs == null || defs.isEmpty()) {
                    view.panelSearch.resultModel.addElement("No results found for: " + slang);
                } else {
                    for (String def : defs) {
                        view.panelSearch.resultModel.addElement(def);
                    }
                }
            } catch (Exception ex) {
                view.panelSearch.resultModel.clear();
                view.panelSearch.resultModel.addElement("Error: " + ex.getMessage());
            }
        }
    }
    private void searchDefinition() {
        String definition = JOptionPane.showInputDialog(view, "Enter definition:");
        if (definition != null && !definition.trim().isEmpty()) {
            try {
                List<String> slangs = dictionary.searchDefinition(definition);
                view.panelSearch.resultModel.clear();
                if (slangs == null || slangs.isEmpty()) {
                    view.panelSearch.resultModel.addElement("No results found for: " + definition);
                } else {
                    for (String slang : slangs) {
                        view.panelSearch.resultModel.addElement(slang);
                    }
                }
            } catch (Exception ex) {
                view.panelSearch.resultModel.clear();
                view.panelSearch.resultModel.addElement("Error: " + ex.getMessage());
            }
        }
    }
    private void loadHistory() {
        try {
            List<String> history = dictionary.showHistory();
            view.panelHistory.model.clear();
            if (history == null || history.isEmpty()) {
                view.panelHistory.model.addElement("No history");
            } else {
                for (String item : history) {
                    view.panelHistory.model.addElement(item);
                }
            }
        } catch (Exception ex) {
            view.panelHistory.model.clear();
            view.panelHistory.model.addElement("Error loading history: " + ex.getMessage());
        }
    }
    private void addSlang() throws Exception {
        view.panelManage.resultArea.setText("");
        String slang = JOptionPane.showInputDialog(view, "Enter slang");
        String def = JOptionPane.showInputDialog(view, "Enter new definition");
        if (slang == null || def == null) {
            return;
        }
        slang = slang.toUpperCase();
        List<String> newDefs = new ArrayList<>();
        newDefs.add(def);
        List<String> newSlangs = new ArrayList<>();
        newSlangs.add(slang);
        int res = dictionary.addSlang(slang, def, newDefs, newSlangs);
        if (res == 1) {
            String[] options = {"Overwrite", "Duplicate"};
            int choice = JOptionPane.showOptionDialog(
                null,
                "Slang word already exists. Choose an action:",
                "Slang Exists",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
            );
            String cases = dictionary.doChoice(choice, slang, def, newDefs, newSlangs);
            if (cases.equals("OVERWRITTEN")) {
                view.panelManage.resultArea.append("Slang word overwritten.\n");
            }
            else if (cases.equals("DUPLICATED")) {
                view.panelManage.resultArea.append("Slang word duplicated.\n");
            }
            else if (cases.equals("EXISTED DEF")) {
                view.panelManage.resultArea.append("Definition already exists for this slang word.\n");
            }
            else {
                view.panelManage.resultArea.append("Invalid choice");
            }
        }    
        else if (res == 2) {
            view.panelManage.resultArea.append("Slang word added.\n");
        }
        view.panelManage.resultArea.append("Slang after added \n");
        view.panelManage.resultArea.append(slang + " : " + dictionary.searchSlang(slang));
        dictionary.updateMap();
    }

    private void editSlang() throws Exception{
        view.panelManage.resultArea.setText("");
        String slang = JOptionPane.showInputDialog(view, "Enter slang need to edit:");
        if (slang == null)
            return;
        if (dictionary.editSlang(slang).equals("NOTFOUND"))
            view.panelManage.resultArea.append("Slang word not found.");
        else {
            String[] options = {"Edit slang", "Edit definition"};
            int choice = JOptionPane.showOptionDialog(
                null,
                "Choose an action:",
                "Edit choice",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
            );
            if (choice == 0) {
                String newSlang = JOptionPane.showInputDialog(view, "Enter new slang");
                String cases = dictionary.editSlangWord(newSlang, slang);
                if (cases.equals("EXISTED SLANG")) {
                    view.panelManage.resultArea.append("Slang word already exists.\n");
                }
                else if (cases.equals("EDITED SLANG")) {
                    view.panelManage.resultArea.append("Slang word was edited.\n");
                }
                else 
                    view.panelManage.resultArea.append("Invalid choice");

                view.panelManage.resultArea.append("Slang after edited \n");
                view.panelManage.resultArea.append(newSlang + " : " + dictionary.searchSlang(newSlang));
            }
            else {
                List<String> definitions = dictionary.searchSlang(slang);
                view.panelManage.resultArea.append("Definitions for " + slang + ":\n");
                for (int i = 0; i < definitions.size(); i++) {
                    view.panelManage.resultArea.append((i + 1) + ". " + definitions.get(i) + "\n");
                }
                Object[] defList = definitions.toArray();
                int defOption = JOptionPane.showOptionDialog(
                    null,
                    "Choose a definition to edit:",
                    "Edit definition",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    defList,
                    defList[0]
                );
                if (defOption == JOptionPane.CLOSED_OPTION || defOption < 0) {
                    view.panelManage.resultArea.append("Edit canceled.\n");
                    return;
                }               
                String oldDef = definitions.get(defOption);
                view.panelManage.resultArea.append("You selected: " + oldDef + "\n");

                String newDef = null;
                while (true) {
                    newDef = JOptionPane.showInputDialog(view, "Enter new definition:");

                    if (newDef == null) {
                        view.panelManage.resultArea.append("Edit canceled.\n");
                        return;
                    }
                    newDef = newDef.trim();
                    if (newDef.isEmpty()) {
                        JOptionPane.showMessageDialog(view, "Definition cannot be empty. Try again.");
                        continue;
                    }

                    List<String> existingDefs = dictionary.searchDefinition(slang);
                    if (existingDefs != null && existingDefs.contains(newDef)) {
                        JOptionPane.showMessageDialog(view, "This definition already exists. Try another one.");
                        continue;
                    }
                    break;
                }
                dictionary.editDefinition(newDef, oldDef);
                view.panelManage.resultArea.append("Definition edited\n");

                view.panelManage.resultArea.append("Slang after edited \n");
                view.panelManage.resultArea.append(slang + " : " + dictionary.searchSlang(slang));
            }
        }
        dictionary.updateMap();
    }

    private void deleteSlang() throws Exception{
        view.panelManage.resultArea.setText("");
        String slang = JOptionPane.showInputDialog(view, "Enter slang to delete:");
        if (slang == null) 
            return;
        if (dictionary.searchSlang(slang) == null) {
            view.panelManage.resultArea.append("Slang word not found.");
        }
        else {
            String[] options = {"Yes", "No"};
            int choice = JOptionPane.showOptionDialog(
                null,
                "Are you sure you want to delete this slang word?",
                "Confirm",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
            );
            if (choice == 1)
                return;
            dictionary.deleteSlang(slang);
            view.panelManage.resultArea.append("Slang " + slang + "was deleted");
        }
    }
    private void resetDictionary() {
        dictionary.resetDictionary();
        view.panelManage.resultArea.append("Dictionary reseted");
    }
    private void randomSlang() throws Exception{
        String randomSlang = dictionary.getRandomSlang();
        view.panelRandom.result.setText(randomSlang + ": " + dictionary.searchSlang(randomSlang));
    }
    private void quizSlang() {
        view.panelQuiz.resultArea.setText("");
        String slang = dictionary.getRandomSlang();
        List<String> options = dictionary.quizSlang(slang);
        String correctDef = options.get(0);
        Collections.shuffle(options);

        view.panelQuiz.resultArea.append("What is the definition of: " + slang + "?\n");
        for (int i = 0; i < 4; i++) {
            view.panelQuiz.resultArea.append((i + 1) + ". " + options.get(i) + "\n");
        }

        Object[] defOptions = options.toArray();
            int choice = JOptionPane.showOptionDialog(
                null,
                "Choose your answer",
                "Confirm",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                defOptions,
                defOptions[0]
            );

        if (options.get(choice).equals(correctDef)) {
            view.panelQuiz.resultArea.append("Correct! \n");
        } else {
            view.panelQuiz.resultArea.append("Wrong! The correct answer is: " + correctDef);
        }
    }
    private void quizDefinition() {
        view.panelQuiz.resultArea.setText("");
        String def = dictionary.getRandomDefinition();
        List<String> options = dictionary.quizDefinition(def);
        String correctSlang = options.get(0);
        Collections.shuffle(options);

        view.panelQuiz.resultArea.append("Which slang matches with \"" + def + "\" ?\n");
        for (int i = 0; i < 4; i++) {
            view.panelQuiz.resultArea.append((i + 1) + ". " + options.get(i) + "\n");
        }

        Object[] slangOptions = options.toArray();
            int choice = JOptionPane.showOptionDialog(
                null,
                "Choose your answer",
                "Confirm",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                slangOptions,
                slangOptions[0]
            );

        if (options.get(choice).equals(correctSlang)) {
            view.panelQuiz.resultArea.append("Correct! \n");
        } else {
            view.panelQuiz.resultArea.append("Wrong! The correct answer is: " + correctSlang);
        }
    }
}
