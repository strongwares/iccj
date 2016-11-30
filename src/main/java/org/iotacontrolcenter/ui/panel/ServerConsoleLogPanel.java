package org.iotacontrolcenter.ui.panel;


import org.iotacontrolcenter.ui.properties.locale.Localizer;

import javax.swing.*;
import java.awt.*;

public class ServerConsoleLogPanel extends JPanel {

    private Localizer localizer;
    public JTextArea consoleText;

    public ServerConsoleLogPanel(Localizer localizer) {
        super();
        this.localizer = localizer;
        init();
    }

    private void init() {
        consoleText = new JTextArea(400, 400);
        consoleText.setBackground(Color.black);
        consoleText.setForeground(Color.white);

        JScrollPane scrollPane = new JScrollPane(consoleText);
        scrollPane.setBackground(Color.white);
        scrollPane.getViewport().setBackground(Color.white);
        scrollPane.getVerticalScrollBar().setUnitIncrement(50);

        add(scrollPane);
    }
}
