package controller;

import model.SlangDictionary;
import view.MainMenuView;

import javax.swing.*;

public class MainMenuController {
    private SlangDictionary dictionary;
    private MainMenuView view;

    public MainMenuController(SlangDictionary dictionary, MainMenuView view) {
        this.dictionary = dictionary;
        this.view = view;

        // Gắn sự kiện cho các nút
        view.btnSearchSlang.addActionListener(e -> searchSlang());
        view.btnSearchDefinition.addActionListener(e -> searchDefinition());
        view.btnShowHistory.addActionListener(e -> showHistory());
        view.btnAddSlang.addActionListener(e -> addSlang());
        view.btnEditSlang.addActionListener(e -> editSlang());
        view.btnDeleteSlang.addActionListener(e -> deleteSlang());
        view.btnRandom.addActionListener(e -> dictionary.getRandomSlang());
        view.btnQuizSlang.addActionListener(e -> dictionary.quizSlang());
        view.btnQuizDefinition.addActionListener(e -> dictionary.quizDefinition());
        view.btnReset.addActionListener(e -> dictionary.resetDictionary());
    }

    private void searchSlang() {
        String slang = JOptionPane.showInputDialog(view, "Enter slang:");
        if (slang != null) {
            try {
                JOptionPane.showMessageDialog(view, dictionary.searchSlang(slang));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error searching slang: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchDefinition() {
        String definition = JOptionPane.showInputDialog(view, "Enter definition:");
        if (definition != null) {
            try {
                JOptionPane.showMessageDialog(view, dictionary.searchDefinition(definition));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error searching definition: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void showHistory() {
        try {
            dictionary.showHistory();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error loading history: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void addSlang() {
        String slang = JOptionPane.showInputDialog(view, "Enter slang:");
        String def = JOptionPane.showInputDialog(view, "Enter definition:");
        if (slang != null && def != null) dictionary.addSlang(slang, def);
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
