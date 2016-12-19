package org.iotacontrolcenter.ui.controller.worker;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.iotacontrolcenter.dto.ActionResponse;
import org.iotacontrolcenter.dto.IccrPropertyListDto;
import org.iotacontrolcenter.dto.IotaGetNodeInfoResponseDto;
import org.iotacontrolcenter.ui.app.Constants;
import org.iotacontrolcenter.ui.controller.ServerController;
import org.iotacontrolcenter.ui.panel.ServerPanel;
import org.iotacontrolcenter.ui.properties.locale.Localizer;
import org.iotacontrolcenter.ui.proxy.BadResponseException;
import org.iotacontrolcenter.ui.proxy.ServerProxy;

public class IotaNodeinfoWorker extends ActionResponseAbstractApiWorker {

    public IotaNodeinfoWorker(Localizer localizer, ServerPanel serverPanel,
                               ServerProxy proxy, ServerController ctlr, String action,
                               IccrPropertyListDto actionProps) {
        super(localizer, serverPanel, proxy, ctlr, action, actionProps);
    }

    @Override
    protected void done() {
        //System.out.println(ctlr.name + " " + action + " done");

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

        ctlr.setConnected(bre == null && exc == null);

        if (bre != null) {
            System.out.println(ctlr.name + " " + action + "  bad response: " + bre.errMsgkey +
                    ", " + bre.resp.getMsg());

            serverPanel.addConsoleLogLine(localizer.getLocalText(bre.errMsgkey));
            serverPanel.addConsoleLogLine(bre.resp.getMsg());

            ctlr.setIotaActiveUnknown();

            //UiUtil.showErrorDialog("Server " + ctlr.name + " " + localizer.getLocalText(bre.errMsgkey),
            //        bre.resp.getMsg());
        }
        else if (exc != null) {
            System.out.println(ctlr.name + " " + action + "  exception from proxy: ");
            exc.printStackTrace();

            serverPanel.addConsoleLogLine(localizer.getLocalText("iccrApiException"));
            serverPanel.addConsoleLogLine(exc.getLocalizedMessage());

            //UiUtil.showErrorDialog("Server " + ctlr.name + " " + localizer.getLocalText("getIotaNodeinfoError"),
            //        localizer.getLocalText("iccrApiException") + ": " + exc.getLocalizedMessage());

            ctlr.setIotaActiveUnknown();
        }
        else if (resp != null) {
            String actionStatus = getActionStatusFromResponse(Constants.ACTION_RESPONSE_IOTA_GET_NODEINFO, resp);
            if(actionStatus == null || actionStatus.isEmpty() ||
                    !actionStatus.equals(Constants.ACTION_STATUS_TRUE)) {
                serverPanel.addConsoleLogLine(localizer.getLocalText("getIotaNodeinfoError") +
                        ": " + resp.getMsg());

                System.out.println(ctlr.name + " " + action + " fail, response msg: " +
                        resp.getMsg());
            }
            else {
                // This writes the whole long string into the ICCR console log:
                //serverPanel.addConsoleLogLine("iotaNodeInfo: " + resp.getContent());

                System.out.println(ctlr.name + " " + action + ": " + resp.getContent());

                IotaGetNodeInfoResponseDto dto = null;
                try {
                    Gson gson = new GsonBuilder().create();
                    dto = gson.fromJson(resp.getContent(), IotaGetNodeInfoResponseDto.class);

                    // This could write more selective data points into that log:
                    //serverPanel.addConsoleLogLine("iotaNodeInfo tips: " +
                    //        dto.getTips());

                    serverPanel.footerPanel.dataUpdate(dto);
                }
                catch(Exception e) {
                    System.out.println(action + ", exception mapping json response: " + e);
                }

                System.out.println(ctlr.name + " " + action + " success, response content: " +
                        resp.getContent());
            }
        } else {
            System.out.println(ctlr.name + " " + action + " done: unexpected place...");
        }
    }
}
