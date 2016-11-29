package org.iotacontrolcenter.ui.controller;


import org.iotacontrolcenter.ui.app.Constants;
import org.iotacontrolcenter.ui.dialog.ConfigureServerDialog;
import org.iotacontrolcenter.ui.dialog.OpenServerDialog;
import org.iotacontrolcenter.ui.panel.ServerPanel;
import org.iotacontrolcenter.ui.panel.ServerTabPanel;
import org.iotacontrolcenter.ui.properties.locale.Localizer;
import org.iotacontrolcenter.ui.proxy.ServerProxy;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainController implements ActionListener {

    private Localizer localizer;
    private ServerTabPanel serverTabPanel;
    private ConfigureServerDialog cfgServerDialog;

    public void init() {
        localizer = Localizer.getInstance();
        // TODO need props

        /*
        ServerProxy proxy = new ServerProxy();
        ServerController ctlr = new ServerController(proxy);

        ServerPanel localServer = new ServerPanel(ctlr);
        serverTabPanel.add("Local", localServer);
        */
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        System.out.println(action);
        if(action.equals(Constants.MM_ADD_SERVER_ACTION)) {
            addNewServer();
        }
        else if(action.equals(Constants.MM_OPEN_SERVER_ACTION)) {
            openServer();
        }
        else if(action.equals(Constants.DIALOG_CONFIG_SERVER_CANCEL)) {
            if(cfgServerDialog != null) {
                cfgServerDialog.setVisible(false);
                cfgServerDialog.dispose();
            }
        }
        else if(action.equals(Constants.DIALOG_CONFIG_SERVER_SAVE)) {
            cfgServerDialog.setVisible(false);
            cfgServerDialog.dispose();
        }
    }

    public void setServerTabPanel(ServerTabPanel serverTabPanel) {
        this.serverTabPanel = serverTabPanel;
    }

    private void addNewServer() {
        cfgServerDialog = new ConfigureServerDialog(localizer, localizer.getLocalText("dialogTitleAddServer"));
        cfgServerDialog.setLocationRelativeTo(serverTabPanel);
        cfgServerDialog.addCtlr(this);

        cfgServerDialog.addWindowListener(new WindowAdapter() {
                                        @Override
                                        public void windowClosed(WindowEvent e) {
                                            super.windowClosed(e);
                                            cfgServerDialog = null;
                                        }
                                    });

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                cfgServerDialog.serverIpTextField.requestFocusInWindow();
            }
        });

        cfgServerDialog.setVisible(true);

    }

    private void openServer() {
        OpenServerDialog openServerDialog = new OpenServerDialog(localizer, localizer.getLocalText("dialogTitleOpenServer"));
    }
}
