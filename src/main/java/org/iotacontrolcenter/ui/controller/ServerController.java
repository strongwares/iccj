package org.iotacontrolcenter.ui.controller;


import org.iotacontrolcenter.dto.*;
import org.iotacontrolcenter.ui.app.Constants;
import org.iotacontrolcenter.ui.controller.worker.*;
import org.iotacontrolcenter.ui.dialog.ServerSettingsDialog;
import org.iotacontrolcenter.ui.panel.ServerPanel;
import org.iotacontrolcenter.ui.properties.locale.Localizer;
import org.iotacontrolcenter.ui.properties.source.PropertySource;
import org.iotacontrolcenter.ui.proxy.BadResponseException;
import org.iotacontrolcenter.ui.proxy.ServerProxy;
import org.iotacontrolcenter.ui.util.UiUtil;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

public class ServerController implements ActionListener, TableModelListener {

    public boolean wasConnected = false;
    public Localizer localizer;
    public Properties iccrProps;
    public boolean isConnected = false;
    public String name;
    public IccrIotaNeighborsPropertyDto nbrsDto;
    public PropertySource propertySource;
    public ServerProxy proxy;
    public ServerPanel serverPanel;
    public Properties serverProps;
    public ServerSettingsDialog serverSettingsDialog;

    public ServerController(Localizer localizer, ServerProxy proxy, Properties serverProps) {
        this.localizer = localizer;
        this.proxy = proxy;
        this.serverProps = serverProps;
        this.name = serverProps.getProperty(PropertySource.SERVER_NAME_PROP);
        propertySource = PropertySource.getInstance();
    }

    public void serverSetup() {
        serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogConnectingToIccr"));

        SwingUtilities.invokeLater(() -> {
            initialConnect();
        });
    }

    private void initialConnect() {
        SwingWorker worker = getServerSettingProperties();

        worker.addPropertyChangeListener(e -> {
            if(e.getPropertyName().equals("state")) {
                SwingWorker.StateValue state = (SwingWorker.StateValue)e.getNewValue();
                if(state == SwingWorker.StateValue.DONE) {
                    System.out.println(name + " initialConnect: server settings worker done: isConnected: " +
                            isConnected);
                    if (isConnected) {
                        serverActionGetConfigNbrsList();
                        serverActionStatusIota();
                    }
                }
            }
        });
    }

    /*
    private void doConnect() {
        boolean isSuccess = connectToServer();
        if(!isSuccess) {
            return;
        }

        serverPanel.neighborPanel.setNeighbors(serverActionGetConfigNbrsList());

        serverPanel.neighborPanel.save.setEnabled(false);

        serverActionStatusIota();
    }
    */

    /*
    private boolean connectToServer() {
        boolean rval = true;
        dana
        Properties props = getServerSettingProperties();
        rval = props != null;
        logIsConnected(rval);
        return rval;
    }
    */

    public void logIsConnected(boolean isConnected) {
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
    public void tableChanged(TableModelEvent e) {
        int changeType = e.getType();
        int row = e.getFirstRow();
        int col = e.getColumn();

        if(changeType == TableModelEvent.DELETE) {
            System.out.println(name + " server nbr table change event " + changeType +
                    ", deleted row: " + row);
        }
        else if(changeType == TableModelEvent.INSERT) {
            System.out.println(name + " Server nbr table change event " + changeType +
                    ", inserted row: " + row);

            NeighborDto nbr = serverPanel.neighborPanel.neighborModel.getRow(row);
            System.out.println(name + " new nbr: " + nbr);
        }
        else {
            System.out.println(name + " Server nbr table change event " + changeType +
                    ", row: " + row + ", col: " + col);

            NeighborDto nbr = serverPanel.neighborPanel.neighborModel.getRow(row);
            System.out.println(name + " updated nbr: " + nbr);
        }

        serverPanel.neighborPanel.save.setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        System.out.println(name + " Server controller action: " + action);

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
        else if(action.equals(Constants.NEIGHBOR_PANEL_REMOVE_SELECTED)) {
            nbrPanelRemoveSelected();
        }
        else if(action.equals(Constants.NEIGHBOR_PANEL_ADD_NEW)) {
            nbrPanelAddNew();
        }
        else {
            // TODO: localization
            System.out.println(name + " server controller, unrecognized action: " + action);
            UiUtil.showErrorDialog("Action Error", "Unrecognized action: " + action);
        }
    }

    private boolean nbrPanelRemoveSelected() {

        int row = serverPanel.neighborPanel.neighborTable.getSelectedRow();
        System.out.println(name + " nbrPanelRemoveSelected, row: " + row);

        if(row < 0) {
            UiUtil.showErrorDialog(localizer.getLocalText("dialogNbrErrorTitle"),
                    localizer.getLocalText("dialogNbrErrorUnselectedMsg"));
            return false;
        }

        NeighborDto nbr = serverPanel.neighborPanel.neighborModel.getRow(row);
        String who = nbr.getDescr() != null &&  !nbr.getDescr().isEmpty()  ? nbr.getDescr() : "";

        if(!UiUtil.promptUserYorN(localizer.getLocalText("removeNbrPromptTitle"),
                localizer.getLocalText("removeNbrPromptMsg") + " " + who)) {
            return false;
        }

        serverPanel.neighborPanel.neighborModel.removeNeighbor(row);

        serverPanel.neighborPanel.save.setEnabled(true);

        return true;
    }

    private boolean nbrPanelAddNew() {
        boolean isSuccess = false;

        NeighborDto nbr = new NeighborDto();
        nbr.setActive(true);
        nbr.setKey(propertySource.getNowDateTimestamp());
        nbr.setScheme("udp");
        nbr.setIp("0.0.0.0");
        if(iccrProps !=  null && !iccrProps.getProperty("iotaPortNumber").isEmpty()) {
            nbr.setPort(Integer.valueOf(iccrProps.getProperty("iotaPortNumber")));
        }

        serverPanel.neighborPanel.neighborModel.addNeighbor(nbr);

        serverPanel.neighborPanel.save.setEnabled(true);

        return isSuccess;
    }

    private void nbrPanelSave() {
        boolean isSuccess = false;


        /*
        if(nbrToAdd.isEmpty() && nbrToRemove.isEmpty()) {
            UiUtil.showErrorDialog(localizer.getLocalText("dialogNbrNoChangesTitle"),
                    localizer.getLocalText("dialogNbrErrorNothingToSaveMsg"));
            return false;
        }
        */
        IccrIotaNeighborsPropertyDto nbrs = new IccrIotaNeighborsPropertyDto();

        String errors = "";
        String sep = "";
        for(NeighborDto nbr : serverPanel.neighborPanel.neighborModel.nbrs) {
            System.out.println(name + " saving nbr: " + nbr);
            if(nbr.getIp() == null || nbr.getIp().isEmpty() ||
                    nbr.getIp().equals("0.0.0.0") ||
                    !UiUtil.isValidIpV4(nbr.getIp())) {
                errors += sep + localizer.getLocalText("neighborTableIpError");
                if(sep.isEmpty()) {
                    sep = "\n";
                }
            }
            if(nbr.getScheme() == null || nbr.getScheme().isEmpty()) {
                errors += sep + localizer.getLocalText("neighborTableSchemeError");
                if(sep.isEmpty()) {
                    sep = "\n";
                }
            }
            if(nbr.getPort() <= 0) {
                errors += sep + localizer.getLocalText("neighborTablePortError");
                if(sep.isEmpty()) {
                    sep = "\n";
                }
            }
            if(nbr.getDescr() == null) {
                nbr.setDescr("");
            }
            nbrs.addNeighbor(nbr);
        };

        if(!errors.isEmpty()) {
            UiUtil.showErrorDialog("neighborSaveErrorTitle", errors);
            return;
        }

        serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogApiCallIccrSetNeighborsList"));

        SetNbrsConfigPropertyWorker worker = new SetNbrsConfigPropertyWorker(localizer, serverPanel,  proxy, this, nbrs);
        worker.execute();

        //System.out.println(name + " nbrPanelSave returning");

        /*
        try {
            proxy.iccrSetNbrsConfigProperty(nbrs);
        }
        catch(BadResponseException bre) {
            System.out.println("nbrPanelSave: bad response: " + bre.errMsgkey +
                    ", " + bre.resp.getMsg());

            serverPanel.addConsoleLogLine(localizer.getLocalText(bre.errMsgkey));
            serverPanel.addConsoleLogLine(bre.resp.getMsg());

            UiUtil.showErrorDialog(localizer.getLocalText(bre.errMsgkey),
                    bre.resp.getMsg());

        }
        catch(Exception e) {
            System.out.println("nbrPanelSave exception from proxy: ");
            e.printStackTrace();

            serverPanel.addConsoleLogLine(localizer.getLocalText("iccrApiException"));
            serverPanel.addConsoleLogLine(e.getLocalizedMessage());

            UiUtil.showErrorDialog(localizer.getLocalText("iccrSetNbrsConfigPropertyError"),
                    localizer.getLocalText("iccrApiException") + ": " + e.getLocalizedMessage());
        }

        serverPanel.neighborPanel.save.setEnabled(false);

        //return isSuccess;
        */
    }

    private boolean serverActionInstallIota() {
        serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogApiCallInstallIota"));

        SwingUtilities.invokeLater(() -> {
            doInstallIota();
        });
        return true;
    }

    private void doInstallIota() {
        boolean isSuccess = false;

        IccrPropertyListDto actionProps = new IccrPropertyListDto();
        actionProps.addProperty(new IccrPropertyDto(PropertySource.IOTA_DLD_LINK_PROP,
                propertySource.getIotaDownloadLink()));
        actionProps.addProperty(new IccrPropertyDto(PropertySource.IOTA_DLD_FILENAME_PROP,
                propertySource.getIotaDownloadFilename()));

        InstallIotaWorker worker = new InstallIotaWorker(localizer, serverPanel,  proxy, this,
                                            Constants.IOTA_ACTION_INSTALL, actionProps);
        worker.execute();

        //System.out.println(name + " doInstallIota returning");


        /*
        try {
            ActionResponse resp = proxy.doIotaAction(Constants.IOTA_ACTION_INSTALL, actionProps);
            String actionStatus = getActionStatusFromResponse(Constants.ACTION_RESPONSE_IOTA_INSTALL, resp);
            if(actionStatus == null || actionStatus.isEmpty() ||
                    !actionStatus.equals(Constants.ACTION_STATUS_TRUE)) {
                isSuccess = false;
                serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogIotaNotInstalled") +
                        " " + resp.getMsg());
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

        //return isSuccess;
        */
    }

    public void serverActionStatusIota() {
        serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogApiCallStatusIota") + "...");

        StatusIotaWorker worker = new StatusIotaWorker(localizer, serverPanel,  proxy, this,
                Constants.IOTA_ACTION_STATUS, null);
        worker.execute();

        //System.out.println(name + " serverActionStatusIota returning");

        /*
        boolean isActive = false;
        try {
            ActionResponse resp = proxy.doIotaAction(Constants.IOTA_ACTION_STATUS, null);

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

            System.out.println(name + " statusIota: bad response: " + bre.errMsgkey +
                    ", " + bre.resp.getMsg());

            serverPanel.addConsoleLogLine(localizer.getLocalText(bre.errMsgkey));
            serverPanel.addConsoleLogLine(bre.resp.getMsg());

            UiUtil.showErrorDialog(localizer.getLocalText(bre.errMsgkey),
                    bre.resp.getMsg());

        }
        catch(Exception e) {
            System.out.println(name + " statusIota exception from proxy: ");
            e.printStackTrace();

            serverPanel.addConsoleLogLine(localizer.getLocalText("iccrApiException"));
            serverPanel.addConsoleLogLine(e.getLocalizedMessage());

            UiUtil.showErrorDialog(localizer.getLocalText("statusIotaError"),
                    localizer.getLocalText("iccrApiException") + ": " + e.getLocalizedMessage());
        }

        //return isActive;
        */
    }

    private void serverActionStartIota() {
        serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogApiCallStartIota"));

        SwingUtilities.invokeLater(() -> {
            doStartIota();
        });
    }


    private void doStartIota() {

        StartIotaWorker worker = new StartIotaWorker(localizer, serverPanel,  proxy, this,
                Constants.IOTA_ACTION_START, null);
        worker.execute();

        /*
        boolean isSuccess = false;
        try {
            ActionResponse resp = proxy.doIotaAction(Constants.IOTA_ACTION_START, null);
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
            System.out.println(name + " startIota: bad response: " + bre.errMsgkey +
                    ", " + bre.resp.getMsg());

            serverPanel.addConsoleLogLine(localizer.getLocalText(bre.errMsgkey));
            serverPanel.addConsoleLogLine(bre.resp.getMsg());

            UiUtil.showErrorDialog(localizer.getLocalText(bre.errMsgkey),
                    bre.resp.getMsg());
        }
        catch(Exception e) {
            System.out.println(name + " startIota exception from proxy: ");
            e.printStackTrace();

            serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogIotaNotStarted"));
            serverPanel.addConsoleLogLine(e.getLocalizedMessage());

            UiUtil.showErrorDialog(localizer.getLocalText("startIotaError"),
                    localizer.getLocalText("iccrApiException") + ": " + e.getLocalizedMessage());
        }
        return isSuccess;
        */
    }

    private void serverActionStopIota() {
        serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogApiCallStopIota"));

        SwingUtilities.invokeLater(() -> {
            doStopIota();
        });
    }

    private void doStopIota() {
        StopIotaWorker worker = new StopIotaWorker(localizer, serverPanel,  proxy, this,
                Constants.IOTA_ACTION_STOP, null);
        worker.execute();

        /*
        boolean isSuccess = false;
        try {
            ActionResponse resp = proxy.doIotaAction(Constants.IOTA_ACTION_STOP, null);
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
            System.out.println(name + " stopIota: bad response: " + bre.errMsgkey +
                    ", " + bre.resp.getMsg());

            serverPanel.addConsoleLogLine(localizer.getLocalText(bre.errMsgkey));
            serverPanel.addConsoleLogLine(bre.resp.getMsg());

            UiUtil.showErrorDialog(localizer.getLocalText(bre.errMsgkey),
                    bre.resp.getMsg());

        }
        catch(Exception e) {
            System.out.println(name + " stopIota exception from proxy: ");
            e.printStackTrace();

            serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogIotaNotStopped"));
            serverPanel.addConsoleLogLine(e.getLocalizedMessage());

            UiUtil.showErrorDialog(localizer.getLocalText("stopIotaError"),
                    localizer.getLocalText("iccrApiException") + ": " + e.getLocalizedMessage());

        }
        //return isSuccess;
        */
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

    private void serverActionDeleteDb() {
        if(!UiUtil.promptUserYorN(localizer.getLocalText("deleteIotaDbPromptTitle"),
                localizer.getLocalText("deleteIotaDbPromptMsg"))) {
            return;
        }

        serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogApiCallDeleteDb"));

        DeleteIotaDbWorker worker = new DeleteIotaDbWorker(localizer, serverPanel,  proxy, this,
                Constants.IOTA_ACTION_DELETEDB, null);
        worker.execute();

        /*
        boolean isSuccess = false;
        try {
            ActionResponse resp = proxy.doIotaAction(Constants.IOTA_ACTION_DELETEDB, null);
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
            System.out.println(name + " deleteIotaDb: bad response: " + bre.errMsgkey +
                    ", " + bre.resp.getMsg());

            serverPanel.addConsoleLogLine(localizer.getLocalText(bre.errMsgkey));
            serverPanel.addConsoleLogLine(bre.resp.getMsg());

            UiUtil.showErrorDialog(localizer.getLocalText(bre.errMsgkey),
                    bre.resp.getMsg());

        }
        catch(Exception e) {
            System.out.println(name + " deleteIotaDb exception from proxy: ");
            e.printStackTrace();

            serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogDbNotDeleted"));
            serverPanel.addConsoleLogLine(e.getLocalizedMessage());

            UiUtil.showErrorDialog(localizer.getLocalText("consoleLogApiCallDeleteDb"),
                    localizer.getLocalText("iccrApiException") + ": " + e.getLocalizedMessage());

        }
        //return isSuccess;
        */
    }

    private void serverActionDeleteIota() {
        if(!UiUtil.promptUserYorN(localizer.getLocalText("deleteIotaPromptTitle"),
                localizer.getLocalText("deleteIotaPromptMsg"))) {
            return;
        }

        serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogApiCallDeleteIota"));

        DeleteIotaDbWorker worker = new DeleteIotaDbWorker(localizer, serverPanel,  proxy, this,
                Constants.IOTA_ACTION_DELETE, null);
        worker.execute();


        /*
        boolean isSuccess = false;
        try {
            ActionResponse resp = proxy.doIotaAction(Constants.IOTA_ACTION_DELETE, null);
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
            System.out.println(name + " deleteIota: bad response: " + bre.errMsgkey +
                    ", " + bre.resp.getMsg());

            serverPanel.addConsoleLogLine(localizer.getLocalText(bre.errMsgkey));
            serverPanel.addConsoleLogLine(bre.resp.getMsg());

            UiUtil.showErrorDialog(localizer.getLocalText(bre.errMsgkey),
                    bre.resp.getMsg());

        }
        catch(Exception e) {
            System.out.println(name + " deleteIota exception from proxy: ");
            e.printStackTrace();

            serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogIotaNotDeleted"));
            serverPanel.addConsoleLogLine(e.getLocalizedMessage());

            UiUtil.showErrorDialog(localizer.getLocalText("consoleLogApiCallDeleteIotaError"),
                    localizer.getLocalText("iccrApiException") + ": " + e.getLocalizedMessage());

        }
        //return isSuccess;
        */
    }

    private void serverSettingsDialogSave() {
        if(serverSettingsDialog == null) {
            // TODO: localization
            System.out.println(name + " serverSettingsDialogSave: dialog found");
            UiUtil.showErrorDialog("Settings Save Fail", "Dialog not found");
            return;
        }
        Properties newProps = new Properties();
        if(serverSettingsDialog.propList == null ||
                serverSettingsDialog.propList.isEmpty()) {
            // TODO: localization
            System.out.println(name + " serverSettingsDialogSave: prop list found");
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
            UiUtil.showErrorDialog("dialogSaveErrorTitle", errors);
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
            System.out.println(name + " serverSettingsDialogSave: bad response: " + bre.errMsgkey +
                    ", " + bre.resp.getMsg());

            serverPanel.addConsoleLogLine(localizer.getLocalText(bre.errMsgkey));
            serverPanel.addConsoleLogLine(bre.resp.getMsg());

            UiUtil.showErrorDialog(localizer.getLocalText(bre.errMsgkey),
                    bre.resp.getMsg());

        }
        catch(Exception e) {
            System.out.println(name + " serverSettingsDialogSave exception from proxy: ");
            e.printStackTrace();

            serverPanel.addConsoleLogLine(localizer.getLocalText("iccrSetConfigError"));
            serverPanel.addConsoleLogLine(e.getLocalizedMessage());

            UiUtil.showErrorDialog(localizer.getLocalText("iccrSetConfigError"),
                    localizer.getLocalText("iccrApiException") + ": " + e.getLocalizedMessage());

        }
    }

    private void showServerSettingsDialog() {
        serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogApiCallIccrGetConfig") + "...");


        ShowServerSettingsDialogWorker worker = new ShowServerSettingsDialogWorker(localizer, serverPanel, proxy, this);
        worker.execute();

        /*
        iccrProps = getServerSettingProperties();

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
        */
    }

    public void serverActionGetConfigNbrsList() {
        serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogApiCallIccrGetNeighborsList"));

        GetIccrIotaNeighborsWorker worker = new GetIccrIotaNeighborsWorker(localizer, serverPanel, proxy, this);
        worker.execute();

        //System.out.println(name + " getServerSettingProperties returning");

        /*
        IccrIotaNeighborsPropertyDto dto = null;
        try {
            dto = proxy.iccrGetNbrsConfigProperty();
        }
        catch(BadResponseException bre) {
            System.out.println("serverActionGetConfigNbrsList: bad response: " + bre.errMsgkey +
                    ", " + bre.resp.getMsg());

            serverPanel.addConsoleLogLine(localizer.getLocalText(bre.errMsgkey));
            serverPanel.addConsoleLogLine(bre.resp.getMsg());

            UiUtil.showErrorDialog(localizer.getLocalText(bre.errMsgkey),
                    bre.resp.getMsg());

        }
        catch(Exception e) {
            System.out.println("serverActionGetConfigNbrsList exception from proxy: ");
            e.printStackTrace();

            serverPanel.addConsoleLogLine(localizer.getLocalText("iccrGetNbrsConfigPropertyError"));
            serverPanel.addConsoleLogLine(e.getLocalizedMessage());

            UiUtil.showErrorDialog(localizer.getLocalText("iccrGetNbrsConfigPropertyError"),
                    localizer.getLocalText("iccrApiException") + ": " + e.getLocalizedMessage());

        }
        return dto;
        */
    }

    private SwingWorker getServerSettingProperties() {
        serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogApiCallIccrGetConfig") + "...");

        isConnected = false;
        iccrProps = null;

        GetIccrConfigWorker worker = new GetIccrConfigWorker(localizer, serverPanel, proxy, this);
        worker.execute();

        //System.out.println(name + " getServerSettingProperties returning");
        return worker;

        /*
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
        */
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
                System.out.println(name + ": " + prop.getKey() + " -> " + prop.getValue());
                if (prop.getKey().equals(key)) {
                    val = prop.getValue();
                    break;
                }
            }
        }
        return val;
    }
}
