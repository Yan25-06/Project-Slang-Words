package view;

import javax.swing.*;
import java.awt.*;

public class MainMenuView extends JFrame {

    // ====== 5 nhóm chức năng ======
    public JButton groupSearch = new JButton("Search");
    public JButton groupHistory = new JButton("History");
    public JButton groupManage = new JButton("Manage");
    public JButton groupRandom = new JButton("Random");
    public JButton groupQuiz = new JButton("Quiz");

    // ====== Panel hiển thị chức năng con ======
    public JPanel centerPanel = new JPanel(new CardLayout());

    // ====== Các panel con (nội dung của từng nhóm) ======
    public JPanel panelSearch = new JPanel();
    public JPanel panelHistory = new JPanel();
    public JPanel panelManage = new JPanel();
    public JPanel panelRandom = new JPanel();
    public JPanel panelQuiz = new JPanel();

    // ====== Functional buttons (exposed for controller) ======
    public JButton btnSearchSlang = new JButton("Search slang");
    public JButton btnSearchDefinition = new JButton("Search definition");

    public JButton btnShowHistory = new JButton("Show history");

    public JButton btnAddSlang = new JButton("Add slang");
    public JButton btnEditSlang = new JButton("Edit slang");
    public JButton btnDeleteSlang = new JButton("Delete slang");
    public JButton btnReset = new JButton("Reset dictionary");

    public JButton btnRandom = new JButton("Random slang");

    public JButton btnQuizSlang = new JButton("Quiz slang");
    public JButton btnQuizDefinition = new JButton("Quiz definition");

    public MainMenuView() {

        // =============== FRAME ============
        setTitle("Slang Dictionary");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // =============== TOP MENU BAR ===============
        JPanel topMenu = new JPanel();
        topMenu.setLayout(new GridLayout(1, 5));
        topMenu.setPreferredSize(new Dimension(1000, 60));

        topMenu.add(groupSearch);
        topMenu.add(groupHistory);
        topMenu.add(groupManage);
        topMenu.add(groupRandom);
        topMenu.add(groupQuiz);

        add(topMenu, BorderLayout.NORTH);

        // =============== CARD PANELS ===============
        panelSearch.setLayout(new GridLayout(2, 1, 5, 5));
        panelSearch.add(btnSearchSlang);
        panelSearch.add(btnSearchDefinition);

        panelHistory.setLayout(new GridLayout(1, 1, 5, 5));
        panelHistory.add(btnShowHistory);

        panelManage.setLayout(new GridLayout(4, 1, 5, 5));
        panelManage.add(btnAddSlang);
        panelManage.add(btnEditSlang);
        panelManage.add(btnDeleteSlang);
        panelManage.add(btnReset);

        panelRandom.setLayout(new GridLayout(1, 1, 5, 5));
        panelRandom.add(btnRandom);

        panelQuiz.setLayout(new GridLayout(2, 1, 5, 5));
        panelQuiz.add(btnQuizSlang);
        panelQuiz.add(btnQuizDefinition);

        centerPanel.add(panelSearch, "SEARCH");
        centerPanel.add(panelHistory, "HISTORY");
        centerPanel.add(panelManage, "MANAGE");
        centerPanel.add(panelRandom, "RANDOM");
        centerPanel.add(panelQuiz, "QUIZ");

        add(centerPanel, BorderLayout.CENTER);

        // Top group buttons switch cards
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