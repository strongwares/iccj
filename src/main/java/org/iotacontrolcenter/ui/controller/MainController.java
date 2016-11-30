package org.iotacontrolcenter.ui.controller;


import org.iotacontrolcenter.ui.app.Constants;
import org.iotacontrolcenter.ui.dialog.ConfigureServerDialog;
import org.iotacontrolcenter.ui.dialog.IccSettingsDialog;
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

    private IccSettingsDialog iccSettingsDialog;
    private Localizer localizer;
    private ServerTabPanel serverTabPanel;
    private ConfigureServerDialog cfgServerDialog;
    private OpenServerDialog openServerDialog;

    public void init() {
        localizer = Localizer.getInstance();

        ServerProxy proxy = new ServerProxy();
        ServerController ctlr = new ServerController(localizer, proxy);

        ServerPanel localServer = new ServerPanel("Local", localizer, ctlr);
        ctlr.setServerPanel(localServer);
        serverTabPanel.add("Local", localServer);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        System.out.println(action);
        if(action.equals(Constants.MM_ADD_SERVER_ACTION)) {
            showAddOrEditServerDialog(localizer.getLocalText("dialogTitleAddServer"));
        }
        else if(action.equals(Constants.MM_OPEN_SERVER_ACTION)) {
            showOpenServerDialog();
        }
        else if(action.equals(Constants.MM_ICC_SETTINGS_ACTION)) {
            showIccSettingsDialog();
        }
        else if(action.equals(Constants.DIALOG_CONFIG_SERVER_CANCEL)) {
            if(cfgServerDialog != null) {
                cfgServerDialog.setVisible(false);
                cfgServerDialog.dispose();
            }
        }
        else if(action.equals(Constants.DIALOG_ICC_SETTINGS_CANCEL)) {
            if(iccSettingsDialog != null) {
                iccSettingsDialog.setVisible(false);
                iccSettingsDialog.dispose();
            }
        }
        else if(action.equals(Constants.DIALOG_ICC_SETTINGS_SAVE)) {
            iccSettingsDialog.setVisible(false);
            iccSettingsDialog.dispose();
        }
        else if(action.equals(Constants.DIALOG_CONFIG_SERVER_SAVE)) {
            cfgServerDialog.setVisible(false);
            cfgServerDialog.dispose();
        }
        else if(action.equals(Constants.DIALOG_OPEN_SERVER_OPEN)) {
            openServerDialog.setVisible(false);
            openServerDialog.dispose();
            openSelectedServer();
        }
        else if(action.equals(Constants.DIALOG_OPEN_SERVER_EDIT)) {
            openServerDialog.setVisible(false);
            openServerDialog.dispose();
            showAddOrEditServerDialog(localizer.getLocalText("dialogTitleEditServer"));
        }
        else if(action.equals(Constants.DIALOG_OPEN_SERVER_ADD_SERVER)) {
            openServerDialog.setVisible(false);
            openServerDialog.dispose();
            showAddOrEditServerDialog(localizer.getLocalText("dialogTitleAddServer"));
        }
        else if(action.equals(Constants.DIALOG_OPEN_SERVER_REMOVE)) {
            openServerDialog.setVisible(false);
            openServerDialog.dispose();
        }
        else if(action.equals(Constants.DIALOG_OPEN_SERVER_CANCEL)) {
            openServerDialog.setVisible(false);
            openServerDialog.dispose();
        }
    }

    private void openSelectedServer() {

    }

    public void setServerTabPanel(ServerTabPanel serverTabPanel) {
        this.serverTabPanel = serverTabPanel;
    }

    private void showIccSettingsDialog() {
        iccSettingsDialog = new IccSettingsDialog(localizer);
        iccSettingsDialog.setLocationRelativeTo(serverTabPanel);
        iccSettingsDialog.addCtlr(this);

        iccSettingsDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                iccSettingsDialog = null;
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                iccSettingsDialog.nbrRefreshTimeTextField.requestFocusInWindow();
            }
        });

        iccSettingsDialog.setVisible(true);
    }

    private void showAddOrEditServerDialog(String title) {
        cfgServerDialog = new ConfigureServerDialog(localizer, title);
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

    private void showOpenServerDialog() {
        openServerDialog = new OpenServerDialog(localizer, localizer.getLocalText("dialogTitleOpenServer"));
        openServerDialog.setLocationRelativeTo(serverTabPanel);
        openServerDialog.addCtlr(this);

        openServerDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                openServerDialog = null;
            }
        });

        openServerDialog.setVisible(true);
    }
}
