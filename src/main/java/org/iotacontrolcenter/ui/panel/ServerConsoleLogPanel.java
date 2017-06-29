package org.iotacontrolcenter.ui.panel;


import org.iotacontrolcenter.ui.controller.ServerController;
import org.iotacontrolcenter.ui.properties.locale.Localizer;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class ServerConsoleLogPanel extends JPanel {

    private static final long serialVersionUID = -6272542257359373435L;
    @SuppressWarnings("unused")
    private ServerController ctlr;
    private Localizer localizer;
    public JTextArea consoleText;

    public ServerConsoleLogPanel(Localizer localizer, ServerController ctlr) {
        super();
        this.localizer = localizer;
        this.ctlr = ctlr;
        init();
    }

    private void init() {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5),
                BorderFactory.createRaisedBevelBorder()));

        JPanel p1 = new JPanel();
        //p1.setBackground(Color.gray);
        p1.setLayout(new BorderLayout());
        p1.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));

        JLabel consoleLogLabel = new JLabel(localizer.getLocalText("labelTextConsoleLog") + ":");
        consoleLogLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        p1.add(consoleLogLabel, BorderLayout.NORTH);

        //consoleText = new JList<>();
        consoleText = new JTextArea(22, 30);
        consoleText.setBackground(Color.black);
        consoleText.setForeground(Color.yellow);
        consoleText.setEditable(false);

        DefaultCaret caret = (DefaultCaret)consoleText.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        /*
        JPanel p2 = new JPanel();
        p2.setLayout(new BorderLayout());
        p2.add(consoleText, BorderLayout.CENTER);
        */

        //consoleText.setPreferredScrollableViewportSize(new Dimension(400, 400));
        //consoleText.setFillsViewportHeight(true);

        //JScrollPane scrollPane = new JScrollPane(p2);

        JScrollPane scrollPane = new JScrollPane(consoleText);

        //scrollPane.setBackground(Color.black);
        //scrollPane.getViewport().setBackground(Color.black);
        scrollPane.getVerticalScrollBar().setUnitIncrement(50);

        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        //scrollPane.setPreferredSize(new Dimension(400, 400));

        p1.add(scrollPane, BorderLayout.CENTER);

        setPreferredSize(new Dimension(400, 400));
        add(p1);
    }
}
