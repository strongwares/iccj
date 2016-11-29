package org.iotacontrolcenter.ui.controller;


import org.iotacontrolcenter.ui.panel.ServerPanel;
import org.iotacontrolcenter.ui.panel.ServerTabPanel;
import org.iotacontrolcenter.ui.proxy.ServerProxy;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController implements ActionListener, MenuListener {

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
        System.out.println(e.getActionCommand());
    }

    @Override
    public void menuSelected(MenuEvent e) {
        JMenu menu = (JMenu)e.getSource();
        System.out.println(menu.getActionCommand());

        menu.setSelected(false);
    }

    @Override
    public void menuDeselected(MenuEvent e) {
        JMenu menu = (JMenu)e.getSource();
        System.out.println(menu.getActionCommand() + " deselected");
    }

    @Override
    public void menuCanceled(MenuEvent e) {
        JMenu menu = (JMenu)e.getSource();
        System.out.println(menu.getActionCommand() + " canceled");
    }

    public void setServerTabPanel(ServerTabPanel serverTabPanel) {
        this.serverTabPanel = serverTabPanel;
    }
}
