package view;

import javax.swing.*;
import java.awt.*;

public class MainMenuView extends JFrame {

    public JButton groupSearch = new JButton("Search");
    public JButton groupHistory = new JButton("History");
    public JButton groupManage = new JButton("Manage");
    public JButton groupRandom = new JButton("Random");
    public JButton groupQuiz = new JButton("Quiz");

    public JPanel centerPanel = new JPanel(new CardLayout());

    public SearchPanel panelSearch = new SearchPanel();
    public HistoryPanel panelHistory = new HistoryPanel();
    public ManagePanel panelManage = new ManagePanel();
    public RandomPanel panelRandom = new RandomPanel();
    public QuizPanel panelQuiz = new QuizPanel();

    public MainMenuView() {

        setTitle("Slang Dictionary");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== TOP MENU =====
        JPanel topMenu = new TopMenuPanel(
                groupSearch, groupHistory, groupManage, groupRandom, groupQuiz
        );
        add(topMenu, BorderLayout.NORTH);

        // ===== CENTER (CardLayout) =====
        centerPanel.add(panelSearch, "SEARCH");
        centerPanel.add(panelHistory, "HISTORY");
        centerPanel.add(panelManage, "MANAGE");
        centerPanel.add(panelRandom, "RANDOM");
        centerPanel.add(panelQuiz, "QUIZ");

        add(centerPanel, BorderLayout.CENTER);

        // ===== EVENT SWITCH TABS =====
        groupSearch.addActionListener(e -> showCard("SEARCH"));
        groupHistory.addActionListener(e -> showCard("HISTORY"));
        groupManage.addActionListener(e -> showCard("MANAGE"));
        groupRandom.addActionListener(e -> showCard("RANDOM"));
        groupQuiz.addActionListener(e -> showCard("QUIZ"));

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showCard(String name) {
        CardLayout cl = (CardLayout) centerPanel.getLayout();
        cl.show(centerPanel, name);
    }
}
