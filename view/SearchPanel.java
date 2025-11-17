package view;

import javax.swing.*;
import java.awt.*;

public class SearchPanel extends JPanel {

    public JButton btnSearchSlang = new JButton("Search slang");
    public JButton btnSearchDefinition = new JButton("Search definition");

    public DefaultListModel<String> resultModel = new DefaultListModel<>();
    public JList<String> resultList = new JList<>(resultModel);

    public SearchPanel() {
        setLayout(new BorderLayout(8, 8));

        JPanel topButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));

        btnSearchSlang.setPreferredSize(new Dimension(160, 32));
        btnSearchDefinition.setPreferredSize(new Dimension(160, 32));

        topButtons.add(btnSearchSlang);
        topButtons.add(btnSearchDefinition);

        add(topButtons, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(resultList);
        add(scroll, BorderLayout.CENTER);
    }
}
