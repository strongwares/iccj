package org.iotacontrolcenter.ui.controller.worker;

import org.iotacontrolcenter.dto.IccrIotaNeighborsPropertyDto;
import org.iotacontrolcenter.ui.controller.ServerController;
import org.iotacontrolcenter.ui.panel.ServerPanel;
import org.iotacontrolcenter.ui.properties.locale.Localizer;
import org.iotacontrolcenter.ui.proxy.BadResponseException;
import org.iotacontrolcenter.ui.proxy.ServerProxy;
import org.iotacontrolcenter.ui.util.UiUtil;


public class GetIccrIotaNeighborsWorker extends ObjectAbstractApiWorker {

    public GetIccrIotaNeighborsWorker(Localizer localizer, ServerPanel serverPanel, ServerProxy proxy,
                                      ServerController ctlr) {
        super(localizer, serverPanel, proxy, ctlr);
    }

    @Override
    public Object doInBackground() {
        //System.out.println("GetIccrIotaNeighborsWorker do...");

        try {
            return proxy.iccrGetNbrsConfigProperty();
        }
        catch(BadResponseException bre) {
            return bre;
        }
        catch(Exception exc) {
            return exc;
        }
    }

    @Override
    protected void done() {
        //System.out.println("GetIccrIotaNeighborsWorker done");

        Object rval = null;
        try {
            rval = get();
        }
        catch(Exception e) {
            System.out.println("GetIccrIotaNeighborsWorker done: exception from get: ");
            e.printStackTrace();
        }

        if(rval == null) {
            System.out.println("GetIccrIotaNeighborsWorker done: return value is empty");
            return;
        }

        BadResponseException bre = null;
        Exception exc =  null;
        IccrIotaNeighborsPropertyDto dto = null;

        if(rval instanceof BadResponseException) {
            bre = (BadResponseException)rval;
        }
        else if(rval instanceof Exception) {
            exc = (Exception)rval;
        }
        else if(rval instanceof IccrIotaNeighborsPropertyDto) {
            dto = (IccrIotaNeighborsPropertyDto)rval;
        }
        else {
            System.out.println("GetIccrIotaNeighborsWorker unexpected return type: " + rval.getClass().getCanonicalName());
            return;
        }

        ctlr.setConnected(bre == null && exc == null);

        if (bre != null) {
            System.out.println("GetIccrIotaNeighborsWorker: done: bad response: " + bre.errMsgkey +
                    ", " + bre.resp.getMsg());

            serverPanel.addConsoleLogLine(localizer.getLocalText(bre.errMsgkey));
            serverPanel.addConsoleLogLine(bre.resp.getMsg());

            UiUtil.showErrorDialog("Server " + ctlr.name + " " + localizer.getLocalText(bre.errMsgkey),
                    bre.resp.getMsg());
        }
        else if(exc != null) {
            System.out.println("GetIccrIotaNeighborsWorker done: exception from proxy: ");
            exc.printStackTrace();

            serverPanel.addConsoleLogLine(localizer.getLocalText("iccrGetNbrsConfigPropertyError"));
            serverPanel.addConsoleLogLine(exc.getLocalizedMessage());

            UiUtil.showErrorDialog("Server " + ctlr.name + " " + localizer.getLocalText("iccrGetNbrsConfigPropertyError"),
                    localizer.getLocalText("iccrApiException") + ": " + exc.getLocalizedMessage());
        }
        else {
            //System.out.println("GetIccrIotaNeighborsWorker done: no exceptions");
            ctlr.nbrsDto = dto;

            serverPanel.neighborPanel.setNeighbors(dto);
            serverPanel.neighborPanel.save.setEnabled(false);
        }
    }

}
