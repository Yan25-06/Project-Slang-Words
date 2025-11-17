package controller;

import model.SlangDictionary;
import view.MainMenuView;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class MainMenuController {
    private SlangDictionary dictionary;
    private MainMenuView view;

    public MainMenuController(SlangDictionary dictionary, MainMenuView view) {
        this.dictionary = dictionary;
        this.view = view;

        // ====== LỊCH SỬ ======
        view.groupHistory.addActionListener(e -> loadHistory());

        // ====== SEARCH PANEL ======
        view.panelSearch.btnSearchSlang.addActionListener(e -> searchSlang());
        view.panelSearch.btnSearchDefinition.addActionListener(e -> searchDefinition());

        // ====== MANAGE PANEL ======
        view.panelManage.btnAdd.addActionListener(e -> addSlang());
        view.panelManage.btnEdit.addActionListener(e -> editSlang());
        view.panelManage.btnDelete.addActionListener(e -> deleteSlang());
        view.panelManage.btnReset.addActionListener(e -> dictionary.resetDictionary());

        // ====== RANDOM PANEL ======
        view.panelRandom.btnRandom.addActionListener(e ->
                JOptionPane.showMessageDialog(view, dictionary.getRandomSlang())
        );

        // ====== QUIZ PANEL ======
        view.panelQuiz.btnQuizSlang.addActionListener(e -> dictionary.quizSlang());
        view.panelQuiz.btnQuizDefinition.addActionListener(e -> dictionary.quizDefinition());
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
    private void addSlang() {
        String slang = JOptionPane.showInputDialog(view, "Enter slang:");
        String def = JOptionPane.showInputDialog(view, "Enter definition:");
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
                view.panelManage.resultArea.append("Slang word overwritten.");
            }
            else if (cases.equals("DUPLICATED")) {
                view.panelManage.resultArea.append("Slang word duplicated.");
            }
            else if (cases.equals("EXISTED DEF")) {
                view.panelManage.resultArea.append("Definition already exists for this slang word.");
            }
            else {
                view.panelManage.resultArea.append("Invalid choice");
            }
        }    
        else if (res == 2) {
            view.panelManage.resultArea.append("Slang word added.");
        }
        dictionary.updateMap();
    }

    private void editSlang() {
        String slang = JOptionPane.showInputDialog(view, "Enter slang to edit:");
        if (slang != null) dictionary.editSlang(slang);
    }

    private void deleteSlang() {
        String slang = JOptionPane.showInputDialog(view, "Enter slang to delete:");
        if (slang != null) dictionary.deleteSlang(slang);
    }
}
