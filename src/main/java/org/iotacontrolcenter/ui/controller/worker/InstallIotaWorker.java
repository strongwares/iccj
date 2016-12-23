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

public class InstallIotaWorker extends ActionResponseAbstractApiWorker {

    public InstallIotaWorker(Localizer localizer, ServerPanel serverPanel,
                             ServerProxy proxy, ServerController ctlr, String action,
                             IccrPropertyListDto actionProps) {
        super(localizer, serverPanel, proxy, ctlr, action, actionProps);
    }

    @Override
    public Object doInBackground() {
        ctlr.serverPanel.serverActionPanel.setDownloadActive(true);
        return super.doInBackground();
    }

    @Override
    protected void done() {
        //System.out.println(ctlr.name + " InstallIotaWorker done");

        ctlr.serverPanel.serverActionPanel.setDownloadActive(false);

        Object rval = null;
        try {
            rval = get();
        }
        catch(Exception e) {
            System.out.println(ctlr.name + " " + action + " done: exception from get: ");
            e.printStackTrace();
        }

        if(rval == null) {
            System.out.println(ctlr.name + " " + action + " done: return value is empty");
            return;
        }

        BadResponseException bre = null;
        Exception exc =  null;
        ActionResponse resp = null;

        if(rval instanceof BadResponseException) {
            bre = (BadResponseException)rval;
        }
        else if(rval instanceof Exception) {
            exc = (Exception)rval;
        }
        else if(rval instanceof ActionResponse) {
            resp = (ActionResponse)rval;
        }
        else {
            System.out.println(ctlr.name + " " + action + " unexpected return type: " + rval.getClass().getCanonicalName());
            return;
        }

        ctlr.setConnected(bre == null && exc == null);

        if (bre != null) {
            System.out.println(ctlr.name + " InstallIotaWorker: bad response: " + bre.errMsgkey +
                    ", " + bre.resp.getMsg());

            serverPanel.addConsoleLogLine(localizer.getLocalText(bre.errMsgkey));
            serverPanel.addConsoleLogLine(bre.resp.getMsg());

            UiUtil.showErrorDialog("Server " + ctlr.name + " " + localizer.getLocalText(bre.errMsgkey),
                    bre.resp.getMsg());
        }
        else if (exc != null) {
            System.out.println(ctlr.name + " InstallIotaWorker exception from proxy: ");
            exc.printStackTrace();

            serverPanel.addConsoleLogLine(localizer.getLocalText("iccrApiException"));
            serverPanel.addConsoleLogLine(exc.getLocalizedMessage());

            UiUtil.showErrorDialog("Server " + ctlr.name + " " + localizer.getLocalText("installIotaError"),
                    localizer.getLocalText("iccrApiException") + ": " + exc.getLocalizedMessage());
        }
        else if(resp != null) {
            String actionStatus = getActionStatusFromResponse(Constants.ACTION_RESPONSE_IOTA_INSTALL, resp);
            if(actionStatus == null || actionStatus.isEmpty() ||
                    !actionStatus.equals(Constants.ACTION_STATUS_TRUE)) {
                serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogIotaNotInstalled") +
                        " " + resp.getMsg());
            }
            else {
                serverPanel.addConsoleLogLine(localizer.getLocalText("consoleLogIotaIsInstalled"));
            }
        }
        else {
            System.out.println(ctlr.name + " InstallIotaWorker done: unexpected place...");
        }
    }
}
