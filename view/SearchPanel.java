package view;

import javax.swing.*;
import java.awt.*;

public class SearchPanel extends JPanel {

    public JButton btnSearchSlang = new JButton("Search slang");
    public JButton btnSearchDefinition = new JButton("Search definition");

    public JLabel resultLabel = new JLabel();

    public SearchPanel() {
        setLayout(new BorderLayout(8, 8));

        // --- TOP: Buttons ---
        JPanel topButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        btnSearchSlang.setPreferredSize(new Dimension(160, 32));
        btnSearchDefinition.setPreferredSize(new Dimension(160, 32));

        topButtons.add(btnSearchSlang);
        topButtons.add(btnSearchDefinition);
        add(topButtons, BorderLayout.NORTH);

        // --- CENTER: JLabel ---
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        resultLabel.setVerticalAlignment(JLabel.TOP);
        resultLabel.setHorizontalAlignment(JLabel.CENTER);

        JScrollPane scrollPane = new JScrollPane(resultLabel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);

        add(scrollPane, BorderLayout.CENTER);
    }
}
