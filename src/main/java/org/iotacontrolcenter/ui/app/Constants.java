package org.iotacontrolcenter.ui.app;

public class Constants {

    public static final String IOTA_ACTION_INSTALL = "install";
    public static final String IOTA_ACTION_START = "start";
    public static final String IOTA_ACTION_STOP = "stop";
    public static final String IOTA_ACTION_RESTART = "restart";
    public static final String IOTA_ACTION_STATUS = "status";
    public static final String IOTA_ACTION_DELETEDB = "deletedb";
    public static final String IOTA_ACTION_DELETE = "delete";

    public static final String ACTION_STATUS_TRUE = "true";
    //  These must be same as action strings in the action impl classes
    // in ICCR
    public static final String ACTION_RESPONSE_IOTA_STATUS = "statusIota";
    public static final String ACTION_RESPONSE_IOTA_INSTALL = "installIota";
    public static final String ACTION_RESPONSE_IOTA_START = "startIota";
    public static final String ACTION_RESPONSE_IOTA_STOP = "stopIota";
    public static final String ACTION_RESPONSE_IOTA_DELETE = "deleteIota";
    public static final String ACTION_RESPONSE_IOTA_DELETE_DB = "deleteIotaDb";
    public static final String ACTION_RESPONSE_IOTA_GET_NODEINFO = "getIotaNodeInfo";
    public static final String ACTION_RESPONSE_IOTA_GET_NBRS = "getIotaNeighbors";

    public static final String MM_ICC_SETTINGS_ACTION = "mm-icc-settings";
    public static final String MM_OPEN_SERVER_ACTION = "mm-open-server";
    public static final String MM_ADD_SERVER_ACTION = "mm-add-server";

    public static final String DIALOG_SERVER_SETTINGS_CANCEL = "dialog-server-settings-cancel";
    public static final String DIALOG_SERVER_SETTINGS_SAVE = "dialog-server-settings-save";

    public static final String DIALOG_CONFIG_SERVER_CANCEL = "dialog-cfg-server-cancel";
    public static final String DIALOG_CONFIG_ADD_SERVER_SAVE = "dialog-cfg-add-server-save";
    public static final String DIALOG_CONFIG_EDIT_SERVER_SAVE = "dialog-cfg-edit-server-save";

    public static final String DIALOG_OPEN_SERVER_OPEN = "dialog-cfg-server-open";
    public static final String DIALOG_OPEN_SERVER_EDIT = "dialog-cfg-server-edit";
    public static final String DIALOG_OPEN_SERVER_ADD_SERVER = "dialog-cfg-server-add-server";
    public static final String DIALOG_OPEN_SERVER_CANCEL = "dialog-open-server-cancel";
    public static final String DIALOG_OPEN_SERVER_REMOVE = "dialog-open-server-remove";

    public static final String DIALOG_ICC_SETTINGS_SAVE = "dialog-icc-settings-save";
    public static final String DIALOG_ICC_SETTINGS_CANCEL = "dialog-icc-settings-cancel";

    public static final String NEIGHBOR_PANEL_SAVE_CHANGES = "nbr-panel-save-changes";
    public static final String NEIGHBOR_PANEL_REMOVE_SELECTED = "nbr-panel-remove-selected";
    public static final String NEIGHBOR_PANEL_ADD_NEW = "nbr-panel-add-new";


    public static final String SERVER_ACTION_SETTINGS = "svr-action-settings";
    public static final String SERVER_ACTION_INSTALL_IOTA = "svr-action-install-iota";
    public static final String SERVER_ACTION_START_IOTA = "svr-action-start-iota";
    public static final String SERVER_ACTION_STATUS_IOTA = "svr-action-status-iota";
    public static final String SERVER_ACTION_STOP_IOTA = "svr-action-stop-iota";
    public static final String SERVER_ACTION_START_WALLET = "svr-action-start-wallet";
    public static final String SERVER_ACTION_DELETEDB_IOTA = "svr-action-deletedb-iota";
    public static final String SERVER_ACTION_UNINSTALL_IOTA = "svr-action-uninstall-iota";
}
