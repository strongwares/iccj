package org.iotacontrolcenter.ui.controller.worker;


import org.iotacontrolcenter.dto.ActionResponse;
import org.iotacontrolcenter.dto.IccrPropertyListDto;
import org.iotacontrolcenter.ui.app.Constants;
import org.iotacontrolcenter.ui.controller.ServerController;
import org.iotacontrolcenter.ui.panel.ServerPanel;
import org.iotacontrolcenter.ui.properties.locale.Localizer;
import org.iotacontrolcenter.ui.proxy.BadResponseException;
import org.iotacontrolcenter.ui.proxy.ServerProxy;
import org.iotacontrolcenter.ui.util.UiUtil;

public class StopIotaWorker extends ActionResponseAbstractApiWorker {

    public StopIotaWorker(Localizer localizer, ServerPanel serverPanel,
                             ServerProxy proxy, ServerController ctlr, String action,
                             IccrPropertyListDto actionProps) {
        super(localizer, serverPanel, proxy, ctlr, action, actionProps);
    }

    @Override
    protected void done() {
        Object rval = null;
        try {
            rval = get();
        } catch (Exception e) {
            System.out.println(ctlr.name + " " + action + " done: exception from get: ");
            e.printStackTrace();
        }

        if (rval == null) {
            System.out.println(ctlr.name + " " + action + " done: return value is empty");
            return;
        }

        BadResponseException bre = null;
        Exception exc = null;
        ActionResponse resp = null;
        boolean isSuccess = false;

        if (rval instanceof BadResponseException) {
            bre = (BadResponseException) rval;
        } else if (rval instanceof Exception) {
            exc = (Exception) rval;
        } else if (rval instanceof ActionResponse) {
            resp = (ActionResponse) rval;
        } else {
            System.out.println(ctlr.name + " " + action + " unexpected return type: " + rval.getClass().getCanonicalName());
            return;
        }

        //ctlr.iotaActive = true;

        ctlr.setConnected(bre == null && exc == null);

        if (bre != null) {
            System.out.println(ctlr.name + " " + action + " bad response: " + bre.errMsgkey +
                    ", " + bre.resp.getMsg());

            serverPanel.addConsoleLogLine(localizer.getLocalText(bre.errMsgkey));
            serverPanel.addConsoleLogLine(bre.resp.getMsg());

            UiUtil.showErrorDialog("Server " + ctlr.name + " " + localizer.getLocalText(bre.errMsgkey),
                    bre.resp.getMsg());
        }
        else if (exc != null) {
            System.out.println(ctlr.name + " " + action + "  exception from proxy: ");
            exc.printStackTrace();

            serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogIotaNotStopped"));
            serverPanel.addConsoleLogLine(exc.getLocalizedMessage());

            UiUtil.showErrorDialog("Server " + ctlr.name + " " + localizer.getLocalText("stopIotaError"),
                    localizer.getLocalText("iccrApiException") + ": " + exc.getLocalizedMessage());

        }
        else if (resp != null) {
            String actionStatus = getActionStatusFromResponse(Constants.ACTION_RESPONSE_IOTA_STOP, resp);
            if(actionStatus == null || actionStatus.isEmpty() ||
                    !actionStatus.equals(Constants.ACTION_STATUS_TRUE)) {
                serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogIotaNotStopped"));
            }
            else {
                serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogIotaIsStopped"));
                ctlr.iotaActive = false;
            }
        } else {
            System.out.println(ctlr.name + " " + action + " done: unexpected place...");

        }
    }
}

