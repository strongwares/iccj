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
import java.util.Properties;

public class ServerController implements ActionListener {

    private Localizer localizer;
    private ServerProxy proxy;
    private ServerPanel serverPanel;
    private Properties serverProps;
    private ServerSettingsDialog serverSettingsDialog;

    public ServerController(Localizer localizer, ServerProxy proxy, Properties serverProps) {
        this.localizer = localizer;
        this.proxy = proxy;
        this.serverProps = serverProps;
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
        else if(action.equals(Constants.DIALOG_SERVER_SETTINGS_CANCEL)) {
            serverSettingsDialogClose();
        }
        else if(action.equals(Constants.DIALOG_SERVER_SETTINGS_SAVE)) {
            serverSettingsDialogSave();
        }
    }

    private void serverSettingsDialogSave() {


        serverSettingsDialogClose();
    }

    private void serverSettingsDialogClose() {
        if(serverSettingsDialog != null) {
            serverSettingsDialog.setVisible(false);
            serverSettingsDialog.dispose();
            serverSettingsDialog = null;
        }
    }

    private void showServerSettingsDialog() {

        Properties iccrProps = null;
        try {
            iccrProps = proxy.iccrGetConfig();
        }
        catch(Exception e) {
            System.out.println("showServerSettingsDialog exception from proxy: ");
            e.printStackTrace();
        }

        serverSettingsDialog = new ServerSettingsDialog(localizer, this);
        serverSettingsDialog.setLocationRelativeTo(serverPanel);

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
