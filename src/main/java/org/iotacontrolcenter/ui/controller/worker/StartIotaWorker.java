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

public class StartIotaWorker extends ActionResponseAbstractApiWorker {

    public StartIotaWorker(Localizer localizer, ServerPanel serverPanel,
                          ServerProxy proxy, ServerController ctlr, String action,
                          IccrPropertyListDto actionProps) {
        super(localizer, serverPanel, proxy, ctlr, action, actionProps);
    }

    @Override
    protected void done() {
        System.out.println(ctlr.name + " " + action + " done");

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

        //ctlr.setIotaActive(false);

        ctlr.setConnected(bre == null && exc == null);

        if (bre != null) {
            System.out.println(ctlr.name + " " + action + " bad response: " + bre.errMsgkey +
                    ", " + bre.resp.getMsg());

            serverPanel.addConsoleLogLine(localizer.getLocalText(bre.errMsgkey));
            serverPanel.addConsoleLogLine(bre.resp.getMsg());

            ctlr.setIotaActive(false);

            UiUtil.showErrorDialog("Server " + ctlr.name + " " + localizer.getLocalText(bre.errMsgkey),
                    bre.resp.getMsg());
        }
        else if (exc != null) {
            System.out.println(ctlr.name + " " + action + " exception from proxy: ");
            exc.printStackTrace();

            serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogIotaNotStarted"));
            serverPanel.addConsoleLogLine(exc.getLocalizedMessage());

            ctlr.setIotaActive(false);

            UiUtil.showErrorDialog("Server " + ctlr.name + " " + localizer.getLocalText("startIotaActionError"),
                    localizer.getLocalText("iccrApiException") + ": " + exc.getLocalizedMessage());

        }
        else if (resp != null) {
            String actionStatus = getActionStatusFromResponse(Constants.ACTION_RESPONSE_IOTA_START, resp);
            if(actionStatus == null || actionStatus.isEmpty() ||
                    !actionStatus.equals(Constants.ACTION_STATUS_TRUE)) {
                serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogIotaNotStarted"));
            }
            else {
                ctlr.setIotaActive(true);
                serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogIotaIsStarted"));
            }
        } else {
            System.out.println(ctlr.name + " " + action + " done: unexpected place...");

        }
    }
}
