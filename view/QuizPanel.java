package view;

import javax.swing.*;
import java.awt.*;

public class QuizPanel extends JPanel {

    public JButton btnQuizSlang = new JButton("Quiz slang");
    public JButton btnQuizDefinition = new JButton("Quiz definition");
    public JTextArea resultArea = new JTextArea();

    public QuizPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // --- TOP PANEL chứa 2 button ---
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 2, 10, 10));
        topPanel.add(btnQuizSlang);
        topPanel.add(btnQuizDefinition);

        add(topPanel, BorderLayout.NORTH);

        // --- Quiz AREA ---
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 20));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setBorder(BorderFactory.createTitledBorder(""));

        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);
    }
}
