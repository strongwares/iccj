package org.iotacontrolcenter.ui.panel;


import org.iotacontrolcenter.ui.controller.ServerController;
import org.iotacontrolcenter.ui.model.NeighborModel;
import org.iotacontrolcenter.ui.properties.locale.Localizer;

import javax.swing.*;
import java.awt.*;

public class ServerPanel extends JPanel {

    private ServerController ctlr;
    private FooterPanel footerPanel;
    private String id;
    private JPanel internalPanel;
    private Localizer localizer;
    public NeighborPanel neighborPanel;
    public ServerActionPanel serverActionPanel;
    public ServerConsoleLogPanel consoleLogPanel;

    public ServerPanel(String id, Localizer localizer, ServerController ctlr) {
        super();
        this.id = id;
        this.localizer = localizer;
        this.ctlr = ctlr;
        init();
    }

    private void init() {
        //setLayout(new BorderLayout(5, 5));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        internalPanel = new JPanel();
        internalPanel.setLayout(new BoxLayout(internalPanel, BoxLayout.X_AXIS));
        internalPanel.setBorder(BorderFactory.createLoweredBevelBorder());

        neighborPanel = new NeighborPanel(localizer, ctlr);
        neighborPanel.setPreferredSize(new Dimension(400,400));
        internalPanel.add(neighborPanel);

        serverActionPanel = new ServerActionPanel(localizer, ctlr);
        internalPanel.add(serverActionPanel);

        consoleLogPanel = new ServerConsoleLogPanel(localizer, ctlr);
        consoleLogPanel.setPreferredSize(new Dimension(400,400));
        internalPanel.add(consoleLogPanel);

        gbc.gridx = gbc.gridy = 0;
        //gbc.gridwidth = 1;
        gbc.weightx = gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(internalPanel, gbc);
        //add(internalPanel, BorderLayout.CENTER);


        footerPanel = new FooterPanel(localizer, ctlr);
        //footerPanel.setPreferredSize(new Dimension());

        //add(footerPanel, BorderLayout.SOUTH);

        gbc.gridy = 1;
        gbc.weightx = gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        //gbc.gridheight = GridBagConstraints.NORTH;
        add(footerPanel, gbc);
    }

    public void addConsoleLogMsg(String msg) {
        consoleLogPanel.consoleText.append(msg);
    }

    public void addConsoleLogLine(String line) {
        consoleLogPanel.consoleText.append(line + "\n");
    }
}
