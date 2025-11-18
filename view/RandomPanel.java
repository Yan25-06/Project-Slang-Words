package view;

import javax.swing.*;
import java.awt.*;

public class RandomPanel extends JPanel {

    public JButton btnRandom = new JButton("Get random slang");
    public JLabel result = new JLabel("", JLabel.CENTER);

    public RandomPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Random Slang Word", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 40));
        add(title, BorderLayout.NORTH);

        // PANEL chứa button và result
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));  // Đặt theo chiều dọc
        center.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnRandom.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRandom.setPreferredSize(new Dimension(200, 40));
        btnRandom.setMaximumSize(new Dimension(200, 40)); // tránh bị kéo giãn

        result.setFont(new Font("Arial", Font.PLAIN, 20));
        result.setAlignmentX(Component.CENTER_ALIGNMENT);
        result.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // cách nút 20px

        center.add(btnRandom);
        center.add(result);

        add(center, BorderLayout.CENTER);
    }
}
