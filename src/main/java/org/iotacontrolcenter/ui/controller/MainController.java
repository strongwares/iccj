package org.iotacontrolcenter.ui.controller;


import org.iotacontrolcenter.ui.panel.ServerPanel;
import org.iotacontrolcenter.ui.panel.ServerTabPanel;
import org.iotacontrolcenter.ui.proxy.ServerProxy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController implements ActionListener {

    private ServerTabPanel serverTabPanel;

    public void init() {
        // TODO need props
        ServerProxy proxy = new ServerProxy();
        ServerController ctlr = new ServerController(proxy);
        ServerPanel localServer = new ServerPanel(ctlr);
        serverTabPanel.add("Local", localServer);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void setServerTabPanel(ServerTabPanel serverTabPanel) {
        this.serverTabPanel = serverTabPanel;
    }
}
