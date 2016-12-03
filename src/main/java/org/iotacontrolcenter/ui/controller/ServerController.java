package org.iotacontrolcenter.ui.controller;


import org.iotacontrolcenter.dto.ActionResponse;
import org.iotacontrolcenter.dto.IccrPropertyDto;
import org.iotacontrolcenter.dto.IccrPropertyListDto;
import org.iotacontrolcenter.dto.SimpleResponse;
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

    private boolean wasConnected = false;
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

    public boolean connectToServer() {
        serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogConnectingToIccr"));
        boolean rval = true;
        Properties props = getServerSettingProperties();
        rval = props != null;
        logIsConnected(rval);
        return rval;
    }

    private void logIsConnected(boolean isConnected) {
        if(isConnected) {
            serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogIsConnectedToIccr"));
        }
        else {
            serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogNotConnectedToIccr"));
        }
        wasConnected = isConnected;
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
        else if(action.equals(Constants.SERVER_ACTION_INSTALL_IOTA)) {
            serverActionInstallIota();
        }
        else if(action.equals(Constants.SERVER_ACTION_START_IOTA)) {
            serverActionStartIota();
        }
        else if(action.equals(Constants.SERVER_ACTION_STATUS_IOTA)) {
            serverActionStatusIota();
        }
        else if(action.equals(Constants.SERVER_ACTION_STOP_IOTA)) {
            serverActionStopIota();
        }
        else if(action.equals(Constants.SERVER_ACTION_START_WALLET)) {
            serverActionStartWallet();
        }
        else if(action.equals(Constants.SERVER_ACTION_DELETEDB_IOTA)) {
            serverActionDeleteDb();
        }
        else if(action.equals(Constants.SERVER_ACTION_UNINSTALL_IOTA)) {
            serverActionDeleteIota();
        }
        else if(action.equals(Constants.NEIGHBOR_PANEL_SAVE_CHANGES)) {
            nbrPanelSave();
        }
        else {
            // TODO: localization
            System.out.println("server controller, unrecognized action: " + action);
            UiUtil.showErrorDialog("Action Error", "Unrecognized action: " + action);
        }
    }

    private boolean nbrPanelSave() {
        //serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogApiCallInstallIota"));
        boolean isSuccess = false;


        return isSuccess;
    }

    private boolean serverActionInstallIota() {
        serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogApiCallInstallIota"));

        SwingUtilities.invokeLater(() -> {
            doInstallIota();
        });
        return true;
    }

    private boolean doInstallIota() {
        boolean isSuccess = false;
        try {
            ActionResponse resp = proxy.doIotaAction(Constants.IOTA_ACTION_INSTALL);
            String actionStatus = getActionStatusFromResponse(Constants.ACTION_RESPONSE_IOTA_INSTALL, resp);
            if(actionStatus == null || actionStatus.isEmpty() ||
                    !actionStatus.equals(Constants.ACTION_STATUS_TRUE)) {
                isSuccess = false;
                serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogIotaNotInstalled"));
            }
            else {
                isSuccess = true;
                serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogIotaIsInstalled"));
            }
        }
        catch(BadResponseException bre) {
            System.out.println("installIota: bad response: " + bre.errMsgkey +
                    ", " + bre.resp.getMsg());

            serverPanel.addConsoleLogLine(localizer.getLocalText(bre.errMsgkey));
            serverPanel.addConsoleLogLine(bre.resp.getMsg());

            UiUtil.showErrorDialog(localizer.getLocalText(bre.errMsgkey),
                    bre.resp.getMsg());

        }
        catch(Exception e) {
            System.out.println("installIota exception from proxy: ");
            e.printStackTrace();

            serverPanel.addConsoleLogLine(localizer.getLocalText("iccrApiException"));
            serverPanel.addConsoleLogLine(e.getLocalizedMessage());

            UiUtil.showErrorDialog(localizer.getLocalText("installIotaError"),
                    localizer.getLocalText("iccrApiException") + ": " + e.getLocalizedMessage());
        }

        return isSuccess;
    }

    public boolean serverActionStatusIota() {
        serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogApiCallStatusIota"));

        boolean isActive = false;
        try {
            ActionResponse resp = proxy.doIotaAction(Constants.IOTA_ACTION_STATUS);

            String actionStatus = getActionStatusFromResponse(Constants.ACTION_RESPONSE_IOTA_STATUS, resp);
            if(actionStatus == null || actionStatus.isEmpty() ||
                    !actionStatus.equals(Constants.ACTION_STATUS_TRUE)) {
                isActive = false;
                serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogIotaNotActive"));
            }
            else {
                isActive = true;
                serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogIotaIsActive"));
            }
        }
        catch(BadResponseException bre) {

            System.out.println("statusIota: bad response: " + bre.errMsgkey +
                    ", " + bre.resp.getMsg());

            serverPanel.addConsoleLogLine(localizer.getLocalText(bre.errMsgkey));
            serverPanel.addConsoleLogLine(bre.resp.getMsg());

            UiUtil.showErrorDialog(localizer.getLocalText(bre.errMsgkey),
                    bre.resp.getMsg());

        }
        catch(Exception e) {
            System.out.println("statusIota exception from proxy: ");
            e.printStackTrace();

            serverPanel.addConsoleLogLine(localizer.getLocalText("iccrApiException"));
            serverPanel.addConsoleLogLine(e.getLocalizedMessage());

            UiUtil.showErrorDialog(localizer.getLocalText("statusIotaError"),
                    localizer.getLocalText("iccrApiException") + ": " + e.getLocalizedMessage());
        }

        return isActive;
    }

    private boolean serverActionStartIota() {
        serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogApiCallStartIota"));

        SwingUtilities.invokeLater(() -> {
            doStartIota();
        });
        return true;
    }


    private boolean doStartIota() {
        boolean isSuccess = false;
        try {
            ActionResponse resp = proxy.doIotaAction(Constants.IOTA_ACTION_START);
            String actionStatus = getActionStatusFromResponse(Constants.ACTION_RESPONSE_IOTA_START, resp);
            if(actionStatus == null || actionStatus.isEmpty() ||
                    !actionStatus.equals(Constants.ACTION_STATUS_TRUE)) {
                isSuccess = false;
                serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogIotaNotStarted"));
            }
            else {
                isSuccess = true;
                serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogIotaIsStarted"));
            }
        }
        catch(BadResponseException bre) {
            System.out.println("startIota: bad response: " + bre.errMsgkey +
                    ", " + bre.resp.getMsg());

            serverPanel.addConsoleLogLine(localizer.getLocalText(bre.errMsgkey));
            serverPanel.addConsoleLogLine(bre.resp.getMsg());

            UiUtil.showErrorDialog(localizer.getLocalText(bre.errMsgkey),
                    bre.resp.getMsg());
        }
        catch(Exception e) {
            System.out.println("startIota exception from proxy: ");
            e.printStackTrace();

            serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogIotaNotStarted"));
            serverPanel.addConsoleLogLine(e.getLocalizedMessage());

            UiUtil.showErrorDialog(localizer.getLocalText("startIotaError"),
                    localizer.getLocalText("iccrApiException") + ": " + e.getLocalizedMessage());
        }
        return isSuccess;
    }

    private boolean serverActionStopIota() {
        serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogApiCallStopIota"));

        SwingUtilities.invokeLater(() -> {
            doStopIota();
        });
        return true;
    }

    private boolean doStopIota() {
        boolean isSuccess = false;
        try {
            ActionResponse resp = proxy.doIotaAction(Constants.IOTA_ACTION_STOP);
            String actionStatus = getActionStatusFromResponse(Constants.ACTION_RESPONSE_IOTA_STOP, resp);
            if(actionStatus == null || actionStatus.isEmpty() ||
                    !actionStatus.equals(Constants.ACTION_STATUS_TRUE)) {
                isSuccess = false;
                serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogIotaNotStopped"));
            }
            else {
                isSuccess = true;
                serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogIotaIsStopped"));
            }
        }
        catch(BadResponseException bre) {
            System.out.println("stopIota: bad response: " + bre.errMsgkey +
                    ", " + bre.resp.getMsg());

            serverPanel.addConsoleLogLine(localizer.getLocalText(bre.errMsgkey));
            serverPanel.addConsoleLogLine(bre.resp.getMsg());

            UiUtil.showErrorDialog(localizer.getLocalText(bre.errMsgkey),
                    bre.resp.getMsg());

        }
        catch(Exception e) {
            System.out.println("stopIota exception from proxy: ");
            e.printStackTrace();

            serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogIotaNotStopped"));
            serverPanel.addConsoleLogLine(e.getLocalizedMessage());

            UiUtil.showErrorDialog(localizer.getLocalText("stopIotaError"),
                    localizer.getLocalText("iccrApiException") + ": " + e.getLocalizedMessage());

        }
        return isSuccess;
    }

    private boolean serverActionStartWallet() {
        serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogApiCallStartWallet"));
        boolean isSuccess = false;
        // TODO
        /*
        try {
            proxy.();
        }
        catch(BadResponseException bre) {
            System.out.println("installIota: bad response: " + bre.errMsgkey +
                    ", " + bre.resp.getMsg());
            UiUtil.showErrorDialog(localizer.getLocalText(bre.errMsgkey),
                    bre.resp.getMsg());

            serverPanel.addConsoleLogLine(bre.resp.getMsg());
        }
        catch(Exception e) {
            System.out.println("installIota exception from proxy: ");
            e.printStackTrace();

            UiUtil.showErrorDialog(localizer.getLocalText("installIotaError"),
                    localizer.getLocalText("iccrApiException") + ": " + e.getLocalizedMessage());

            serverPanel.addConsoleLogLine(e.getLocalizedMessage());
        }
        */
        return isSuccess;
    }

    private boolean serverActionDeleteDb() {
        if(!UiUtil.promptUserYorN(localizer.getLocalText("deleteIotaDbPromptTitle"),
                localizer.getLocalText("deleteIotaDbPromptMsg"))) {
            return false;
        }

        serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogApiCallDeleteDb"));

        boolean isSuccess = false;
        try {
            ActionResponse resp = proxy.doIotaAction(Constants.IOTA_ACTION_DELETEDB);
            String actionStatus = getActionStatusFromResponse(Constants.ACTION_RESPONSE_IOTA_DELETE_DB, resp);
            if(actionStatus == null || actionStatus.isEmpty() ||
                    !actionStatus.equals(Constants.ACTION_STATUS_TRUE)) {
                isSuccess = false;
                serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogDbNotDeleted"));
            }
            else {
                isSuccess = true;
                serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogDbIsDeleted"));
            }
        }
        catch(BadResponseException bre) {
            System.out.println("deleteIotaDb: bad response: " + bre.errMsgkey +
                    ", " + bre.resp.getMsg());

            serverPanel.addConsoleLogLine(localizer.getLocalText(bre.errMsgkey));
            serverPanel.addConsoleLogLine(bre.resp.getMsg());

            UiUtil.showErrorDialog(localizer.getLocalText(bre.errMsgkey),
                    bre.resp.getMsg());

        }
        catch(Exception e) {
            System.out.println("deleteIotaDb exception from proxy: ");
            e.printStackTrace();

            serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogDbNotDeleted"));
            serverPanel.addConsoleLogLine(e.getLocalizedMessage());

            UiUtil.showErrorDialog(localizer.getLocalText("consoleLogApiCallDeleteDb"),
                    localizer.getLocalText("iccrApiException") + ": " + e.getLocalizedMessage());

        }
        return isSuccess;
    }

    private boolean serverActionDeleteIota() {
        if(!UiUtil.promptUserYorN(localizer.getLocalText("deleteIotaPromptTitle"),
                localizer.getLocalText("deleteIotaPromptMsg"))) {
            return false;
        }
        
        serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogApiCallDeleteIota"));

        boolean isSuccess = false;
        try {
            ActionResponse resp = proxy.doIotaAction(Constants.IOTA_ACTION_DELETE);
            String actionStatus = getActionStatusFromResponse(Constants.ACTION_RESPONSE_IOTA_DELETE, resp);
            if(actionStatus == null || actionStatus.isEmpty() ||
                    !actionStatus.equals(Constants.ACTION_STATUS_TRUE)) {
                isSuccess = false;
                serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogIotaNotDeleted"));
            }
            else {
                isSuccess = true;
                serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogIotaIsDeleted"));
            }
        }
        catch(BadResponseException bre) {
            System.out.println("deleteIota: bad response: " + bre.errMsgkey +
                    ", " + bre.resp.getMsg());

            serverPanel.addConsoleLogLine(localizer.getLocalText(bre.errMsgkey));
            serverPanel.addConsoleLogLine(bre.resp.getMsg());

            UiUtil.showErrorDialog(localizer.getLocalText(bre.errMsgkey),
                    bre.resp.getMsg());

        }
        catch(Exception e) {
            System.out.println("deleteIota exception from proxy: ");
            e.printStackTrace();

            serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogIotaNotDeleted"));
            serverPanel.addConsoleLogLine(e.getLocalizedMessage());

            UiUtil.showErrorDialog(localizer.getLocalText("consoleLogApiCallDeleteIotaError"),
                    localizer.getLocalText("iccrApiException") + ": " + e.getLocalizedMessage());

        }
        return isSuccess;
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

            serverPanel.addConsoleLogLine(localizer.getLocalText(bre.errMsgkey));
            serverPanel.addConsoleLogLine(bre.resp.getMsg());

            UiUtil.showErrorDialog(localizer.getLocalText(bre.errMsgkey),
                    bre.resp.getMsg());

        }
        catch(Exception e) {
            System.out.println("serverSettingsDialogSave exception from proxy: ");
            e.printStackTrace();

            serverPanel.addConsoleLogLine(localizer.getLocalText("iccrSetConfigError"));
            serverPanel.addConsoleLogLine(e.getLocalizedMessage());

            UiUtil.showErrorDialog(localizer.getLocalText("iccrSetConfigError"),
                    localizer.getLocalText("iccrApiException") + ": " + e.getLocalizedMessage());

        }
    }

    private void showServerSettingsDialog() {
        Properties iccrProps = getServerSettingProperties();

        if(iccrProps == null) {
            logIsConnected(false);
            return;
        }
        if(!wasConnected) {
            logIsConnected(true);
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

            serverPanel.addConsoleLogLine(localizer.getLocalText(bre.errMsgkey));
            serverPanel.addConsoleLogLine(bre.resp.getMsg());

            UiUtil.showErrorDialog(localizer.getLocalText(bre.errMsgkey),
                    bre.resp.getMsg());

        }
        catch(Exception e) {
            System.out.println("showServerSettingsDialog exception from proxy: ");
            e.printStackTrace();

            serverPanel.addConsoleLogLine(localizer.getLocalText("iccrGetConfigError"));
            serverPanel.addConsoleLogLine(e.getLocalizedMessage());

            UiUtil.showErrorDialog(localizer.getLocalText("iccrGetConfigError"),
                    localizer.getLocalText("iccrApiException") + ": " + e.getLocalizedMessage());

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

    private String getActionStatusFromResponse(String key, ActionResponse ar) {
        String val = null;
        if(ar != null && ar.getProperties() !=  null) {
            for (IccrPropertyDto prop : ar.getProperties()) {
                System.out.println(prop.getKey() + " -> " + prop.getValue());
                if (prop.getKey().equals(key)) {
                    val = prop.getValue();
                    break;
                }
            }
        }
        return val;
    }
}
