package org.iotacontrolcenter.ui.panel;


import org.iotacontrolcenter.ui.controller.ServerController;

import javax.swing.*;

public class ServerPanel extends JPanel {

    private ServerController ctlr;

    public ServerPanel(ServerController ctlr) {
        super();
        this.ctlr = ctlr;
    }
}
