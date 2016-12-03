package org.iotacontrolcenter.ui.controller;


import org.iotacontrolcenter.dto.IccrPropertyListDto;
import org.iotacontrolcenter.ui.app.Constants;
import org.iotacontrolcenter.ui.dialog.ServerSettingsDialog;
import org.iotacontrolcenter.ui.panel.ServerPanel;
import org.iotacontrolcenter.ui.properties.locale.Localizer;
import org.iotacontrolcenter.ui.properties.source.PropertySource;
import org.iotacontrolcenter.ui.proxy.BadResponseException;
import org.iotacontrolcenter.ui.proxy.ServerProxy;
import org.iotacontrolcenter.ui.util.UiUtil;

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
        if(serverSettingsDialog == null) {
            // TODO: localization
            System.out.println("serverSettingsDialogSave: dialog found");
            UiUtil.showErrorDialog("Settings Save Fail", "Dialog not found");
            return;
        }
        Properties newProps = new Properties();
        if(serverSettingsDialog.propList == null ||
                serverSettingsDialog.propList.isEmpty()) {
            // TODO: localization
            System.out.println("serverSettingsDialogSave: prop list found");
            UiUtil.showErrorDialog("Settings Save Fail", "Prop list not found");
            return;
        }

        boolean isChanged = false;

        String iccrPortNumber = serverSettingsDialog.iccrPortTextField.getText();
        if (!serverSettingsDialog.iccrProps.getProperty("iccrPortNumber").equals((iccrPortNumber))) {
            newProps.setProperty("iccrPortNumber", iccrPortNumber);
            isChanged = true;
        }

        String iotaDir = serverSettingsDialog.iotaFolderTextField.getText();
        if (!serverSettingsDialog.iccrProps.getProperty("iotaDir").equals((iotaDir))) {
            newProps.setProperty("iotaDir", iotaDir);
            isChanged = true;
        }

        String iotaNeighborRefreshTime = serverSettingsDialog.nbrRefreshTextField.getText();
        if (!serverSettingsDialog.iccrProps.getProperty("iotaNeighborRefreshTime").equals((iotaNeighborRefreshTime))) {
            newProps.setProperty("iotaNeighborRefreshTime", iotaNeighborRefreshTime);
            isChanged = true;
        }

        String iotaPortNumber = serverSettingsDialog.iotaPortTextField.getText();
        if (!serverSettingsDialog.iccrProps.getProperty("iotaPortNumber").equals((iotaPortNumber))) {
            newProps.setProperty("iotaPortNumber", iotaPortNumber);
            isChanged = true;
        }

        String iotaStartCmd = serverSettingsDialog.iotaStartTextField.getText();
        if (!serverSettingsDialog.iccrProps.getProperty("iotaStartCmd").equals((iotaStartCmd))) {
            newProps.setProperty("iotaStartCmd", iotaStartCmd);
            isChanged = true;
        }

        String errors = "";
        String sep = "";
        boolean isError = false;
        if(iccrPortNumber == null || iccrPortNumber.isEmpty() || !UiUtil.isValidPositiveNumber(iccrPortNumber)) {
            isError = true;
            errors += sep + localizer.getLocalText("dialogSaveErrorInvalidFieldValue") + " " +
                    serverSettingsDialog.iccrPortTextField.getName();
            if(sep.isEmpty()) {
                sep = "\n";
            }
        }
        if(iotaPortNumber == null || iotaPortNumber.isEmpty() || !UiUtil.isValidPositiveNumber(iotaPortNumber)) {
            isError = true;
            errors += sep + localizer.getLocalText("dialogSaveErrorInvalidFieldValue") + " " +
                    serverSettingsDialog.iotaPortTextField.getName();
            if(sep.isEmpty()) {
                sep = "\n";
            }
        }
        if(iotaDir == null || iotaDir.isEmpty()) {
            isError = true;
            errors += sep + localizer.getLocalText("dialogSaveErrorInvalidFieldValue") + " " +
                    serverSettingsDialog.iotaFolderTextField.getName();
            if(sep.isEmpty()) {
                sep = "\n";
            }
        }
        if(iotaNeighborRefreshTime == null || iotaNeighborRefreshTime.isEmpty() || !UiUtil.isValidPositiveNumber(iotaNeighborRefreshTime)) {
            isError = true;
            errors += sep + localizer.getLocalText("dialogSaveErrorInvalidFieldValue") + " " +
                    serverSettingsDialog.nbrRefreshTextField.getName();
            if(sep.isEmpty()) {
                sep = "\n";
            }
        }
        if(iotaStartCmd == null || iotaStartCmd.isEmpty()) {
            isError = true;
            errors += sep + localizer.getLocalText("dialogSaveErrorInvalidFieldValue") + " " +
                    serverSettingsDialog.iotaStartTextField.getName();
            if(sep.isEmpty()) {
                sep = "\n";
            }
        }

        if(isError) {
            // TODO: localization
            UiUtil.showErrorDialog("Server Settings Error", errors);
            return;
        }

        if(!isChanged) {
            UiUtil.showErrorDialog("Server Settings", localizer.getLocalText("settingsUnchanged"));
            return;
        }

        serverSettingsDialogClose();

        serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogApiCallIccrSetConfig"));

        try {
            proxy.iccrSetConfig(newProps);
        }
        catch(BadResponseException bre) {
            System.out.println("serverSettingsDialogSave: bad response: " + bre.errMsgkey +
                    ", " + bre.resp.getMsg());
            UiUtil.showErrorDialog(localizer.getLocalText(bre.errMsgkey),
                    bre.resp.getMsg());

            serverPanel.addConsoleLogLine(bre.resp.getMsg());
        }
        catch(Exception e) {
            System.out.println("serverSettingsDialogSave exception from proxy: ");
            e.printStackTrace();

            UiUtil.showErrorDialog(localizer.getLocalText("iccrGetConfigError"),
                    localizer.getLocalText("iccrApiException") + ": " + e.getLocalizedMessage());

            serverPanel.addConsoleLogLine(e.getLocalizedMessage());
        }
    }

    private void showServerSettingsDialog() {
        Properties iccrProps = getServerSettingProperties();

        if(iccrProps == null) {
            return;
        }

        serverSettingsDialog = new ServerSettingsDialog(localizer, this, iccrProps);
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

    private Properties getServerSettingProperties() {
        serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogApiCallIccrGetConfig"));
        Properties iccrProps = null;
        try {
            iccrProps = proxy.iccrGetConfig();
        }
        catch(BadResponseException bre) {
            System.out.println("showServerSettingsDialog: bad response: " + bre.errMsgkey +
                    ", " + bre.resp.getMsg());
            UiUtil.showErrorDialog(localizer.getLocalText(bre.errMsgkey),
                    bre.resp.getMsg());

            serverPanel.addConsoleLogLine(bre.resp.getMsg());
        }
        catch(Exception e) {
            System.out.println("showServerSettingsDialog exception from proxy: ");
            e.printStackTrace();

            UiUtil.showErrorDialog(localizer.getLocalText("iccrGetConfigError"),
                    localizer.getLocalText("iccrApiException") + ": " + e.getLocalizedMessage());

            serverPanel.addConsoleLogLine(e.getLocalizedMessage());
        }
        return iccrProps;
    }

    private void serverSettingsDialogClose() {
        if(serverSettingsDialog != null) {
            serverSettingsDialog.setVisible(false);
            serverSettingsDialog.dispose();
            serverSettingsDialog = null;
        }
    }
}
