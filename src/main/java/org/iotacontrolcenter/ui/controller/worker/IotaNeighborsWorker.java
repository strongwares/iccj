package org.iotacontrolcenter.ui.controller.worker;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.iotacontrolcenter.dto.ActionResponse;
import org.iotacontrolcenter.dto.IccrPropertyListDto;
import org.iotacontrolcenter.dto.IotaGetNeighborsResponseDto;
import org.iotacontrolcenter.dto.IotaGetNodeInfoResponseDto;
import org.iotacontrolcenter.ui.app.Constants;
import org.iotacontrolcenter.ui.controller.ServerController;
import org.iotacontrolcenter.ui.panel.ServerPanel;
import org.iotacontrolcenter.ui.properties.locale.Localizer;
import org.iotacontrolcenter.ui.proxy.BadResponseException;
import org.iotacontrolcenter.ui.proxy.ServerProxy;
import org.iotacontrolcenter.ui.util.UiUtil;

public class IotaNeighborsWorker extends ActionResponseAbstractApiWorker {

    public IotaNeighborsWorker(Localizer localizer, ServerPanel serverPanel,
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

        if (bre != null) {
            System.out.println(ctlr.name + " " + action + "  bad response: " + bre.errMsgkey +
                    ", " + bre.resp.getMsg());

            serverPanel.addConsoleLogLine(localizer.getLocalText(bre.errMsgkey));
            serverPanel.addConsoleLogLine(bre.resp.getMsg());

            UiUtil.showErrorDialog("Server " + ctlr.name + " " + localizer.getLocalText(bre.errMsgkey),
                    bre.resp.getMsg());
        }
        else if (exc != null) {
            System.out.println(ctlr.name + " " + action + "  exception from proxy: ");
            exc.printStackTrace();

            serverPanel.addConsoleLogLine(localizer.getLocalText("iccrApiException"));
            serverPanel.addConsoleLogLine(exc.getLocalizedMessage());

            UiUtil.showErrorDialog("Server " + ctlr.name + " " + localizer.getLocalText("getIotaNeighborError"),
                    localizer.getLocalText("iccrApiException") + ": " + exc.getLocalizedMessage());

        }
        else if (resp != null) {
            String actionStatus = getActionStatusFromResponse(Constants.ACTION_RESPONSE_IOTA_GET_NBRS, resp);
            if(actionStatus == null || actionStatus.isEmpty() ||
                    !actionStatus.equals(Constants.ACTION_STATUS_TRUE)) {

                serverPanel.addConsoleLogLine(localizer.getLocalText("getIotaNeighborError") +
                        ": " + resp.getMsg());

                System.out.println(ctlr.name + " " + action + " fail, response msg: " +
                        resp.getMsg());
            }
            else {
                //serverPanel.addConsoleLogLine("iotaNeighbors: " + resp.getContent());

                System.out.println(ctlr.name + " " + action + " success, response content: " +
                        resp.getContent());

                IotaGetNeighborsResponseDto dto = null;
                try {
                    Gson gson = new GsonBuilder().create();
                    dto = gson.fromJson(resp.getContent(), IotaGetNeighborsResponseDto.class);

                    serverPanel.neighborPanel.neighborModel.updateNbrInfo(dto);
                }
                catch(Exception e) {
                    System.out.println(action + ", exception mapping json response: " + e);
                }

            }
        } else {
            System.out.println(ctlr.name + " " + action + " done: unexpected place...");
        }
    }
}
