package view;

import javax.swing.*;
import java.awt.*;

public class ManagePanel extends JPanel {

    public JButton btnAdd = new JButton("Add slang");
    public JButton btnEdit = new JButton("Edit slang");
    public JButton btnDelete = new JButton("Delete slang");
    public JButton btnReset = new JButton("Reset dictionary");

    public ManagePanel() {
        setLayout(new BorderLayout(10, 10));

        // LEFT: Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        // buttonPanel.setPreferredSize(new Dimension(150, 200));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnReset);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 200, 10));

        add(buttonPanel, BorderLayout.EAST);

        // CENTER: Result area
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 14));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        JScrollPane resultScrollPane = new JScrollPane(resultArea);
        add(resultScrollPane, BorderLayout.CENTER);
        
        // LEFT: Info text
        JTextArea infoArea = new JTextArea();
        infoArea.setPreferredSize(new Dimension(300, 0));
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Arial", Font.PLAIN, 14));
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setMargin(new Insets(10, 10, 10, 10));

        infoArea.setText(
                "Manage Slang Dictionary:\n" +
                "• Add slang: Add a new slang word with its definition.\n" +
                "• Edit slang: Modify the definition of an existing slang word.\n" +
                "• Delete slang: Remove a slang word from the dictionary.\n" +
                "• Reset dictionary: Restore the dictionary to its original state."
        );
        JScrollPane scrollPane = new JScrollPane(infoArea);
        add(scrollPane, BorderLayout.WEST);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
    }
}