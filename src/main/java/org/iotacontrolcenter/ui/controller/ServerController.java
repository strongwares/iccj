package org.iotacontrolcenter.ui.controller;


import org.iotacontrolcenter.ui.app.Constants;
import org.iotacontrolcenter.ui.dialog.IccSettingsDialog;
import org.iotacontrolcenter.ui.dialog.ServerSettingsDialog;
import org.iotacontrolcenter.ui.panel.ServerPanel;
import org.iotacontrolcenter.ui.properties.locale.Localizer;
import org.iotacontrolcenter.ui.proxy.ServerProxy;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerController implements ActionListener {

    private Localizer localizer;
    private ServerProxy proxy;
    private ServerPanel serverPanel;
    private ServerSettingsDialog serverSettingsDialog;

    public ServerController(Localizer localizer, ServerProxy proxy) {
        this.localizer = localizer;
        this.proxy = proxy;
    }

    public void setServerPanel(ServerPanel serverPanel) {
        this.serverPanel = serverPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        System.out.println("Server controller action: " + action);

        if(action.equals(Constants.SERVER_ACTION_SETTINGS)) {
            showServerSettingsDialog();
        }
    }

    private void showServerSettingsDialog() {
        serverSettingsDialog = new ServerSettingsDialog(localizer);
        serverSettingsDialog.setLocationRelativeTo(serverPanel);
        serverSettingsDialog.addCtlr(this);

        serverSettingsDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                serverSettingsDialog = null;
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                serverSettingsDialog.iccrPortTextField.requestFocusInWindow();
            }
        });

        serverSettingsDialog.setVisible(true);
    }
}
