package view;

import javax.swing.*;
import java.awt.*;

public class RandomPanel extends JPanel {

    public JButton btnRandom = new JButton("Random slang");

    public RandomPanel() {
        setLayout(new GridLayout(1, 1));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(btnRandom);
    }
}
