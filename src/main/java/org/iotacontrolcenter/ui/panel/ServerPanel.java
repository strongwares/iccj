package org.iotacontrolcenter.ui.panel;


import org.iotacontrolcenter.ui.controller.ServerController;
import org.iotacontrolcenter.ui.model.NeighborModel;
import org.iotacontrolcenter.ui.properties.locale.Localizer;

import javax.swing.*;
import java.awt.*;

public class ServerPanel extends JPanel {

    private ServerController ctlr;
    private String id;
    private Localizer localizer;
    private NeighborPanel neighborPanel;
    private ServerActionPanel serverActionPanel;
    private ServerConsoleLogPanel consoleLogPanel;

    public ServerPanel(String id, Localizer localizer, ServerController ctlr) {
        super();
        this.id = id;
        this.localizer = localizer;
        this.ctlr = ctlr;
        init();
    }

    private void init() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        neighborPanel = new NeighborPanel(localizer, ctlr);
        neighborPanel.setPreferredSize(new Dimension(400,400));
        add(neighborPanel);

        serverActionPanel = new ServerActionPanel(localizer, ctlr);
        add(serverActionPanel);

        consoleLogPanel = new ServerConsoleLogPanel(localizer);
        consoleLogPanel.setPreferredSize(new Dimension(400,400));
        add(consoleLogPanel);

    }
}
