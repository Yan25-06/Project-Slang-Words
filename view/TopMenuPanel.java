package view;

import javax.swing.*;
import java.awt.*;

public class TopMenuPanel extends JPanel {

    public TopMenuPanel(JButton... buttons) {
        setLayout(new GridLayout(1, 5, 10, 0));
        setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        setPreferredSize(new Dimension(1000, 60));

        for (JButton btn : buttons) {
            add(wrap(btn));
        }
    }

    private JPanel wrap(JButton btn) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        p.add(btn, BorderLayout.CENTER);
        return p;
    }
}
