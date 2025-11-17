package view;

import javax.swing.*;
import java.awt.*;

public class HistoryPanel extends JPanel {

    public DefaultListModel<String> model = new DefaultListModel<>();
    public JList<String> historyList = new JList<>(model);

    public HistoryPanel() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(historyList);
        add(scroll, BorderLayout.CENTER);
    }
}
