package view;

import javax.swing.*;
import java.awt.*;

public class QuizPanel extends JPanel {

    public JButton btnQuizSlang = new JButton("Quiz slang");
    public JButton btnQuizDefinition = new JButton("Quiz definition");

    public QuizPanel() {
        setLayout(new GridLayout(2, 1, 5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(btnQuizSlang);
        add(btnQuizDefinition);
    }
}
