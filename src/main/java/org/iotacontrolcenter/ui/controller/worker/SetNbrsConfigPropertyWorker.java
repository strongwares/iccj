package org.iotacontrolcenter.ui.controller.worker;


import org.iotacontrolcenter.dto.IccrIotaNeighborsPropertyDto;
import org.iotacontrolcenter.ui.controller.ServerController;
import org.iotacontrolcenter.ui.panel.ServerPanel;
import org.iotacontrolcenter.ui.properties.locale.Localizer;
import org.iotacontrolcenter.ui.proxy.BadResponseException;
import org.iotacontrolcenter.ui.proxy.ServerProxy;
import org.iotacontrolcenter.ui.util.UiUtil;

public class SetNbrsConfigPropertyWorker extends ObjectAbstractApiWorker {

    private IccrIotaNeighborsPropertyDto nbrs;

    public SetNbrsConfigPropertyWorker(Localizer localizer, ServerPanel serverPanel,
                                       ServerProxy proxy,  ServerController ctlr,
                                       IccrIotaNeighborsPropertyDto nbrs) {
        super(localizer, serverPanel, proxy, ctlr);
        this.nbrs = nbrs;
    }

    @Override
    public Object doInBackground() {
        System.out.println(ctlr.name + " SetNbrsConfigPropertyWorker do...");

        try {
            proxy.iccrSetNbrsConfigProperty(nbrs);
        }
        catch(BadResponseException bre) {
            return bre;
        }
        catch(Exception exc) {
            return exc;
        }
        return null;
    }

    @Override
    protected void done() {
        System.out.println(ctlr.name + " SetNbrsConfigPropertyWorker done");

        Object rval = null;
        try {
            rval = get();
        }
        catch(Exception e) {
            System.out.println(ctlr.name + " SetNbrsConfigPropertyWorker done: exception from get: ");
            e.printStackTrace();
        }

        BadResponseException bre = null;
        Exception exc =  null;

        if(rval != null) {
            if (rval instanceof BadResponseException) {
                bre = (BadResponseException) rval;
            }
            else if (rval instanceof Exception) {
                exc = (Exception) rval;
            }
            else {
                System.out.println(ctlr.name + " SetNbrsConfigPropertyWorker unexpected return type: " + rval.getClass().getCanonicalName());
                return;
            }
        }

        if(rval != null && bre != null) {
            System.out.println(ctlr.name + " SetNbrsConfigPropertyWorker: bad response: " + bre.errMsgkey +
                    ", " + bre.resp.getMsg());

            serverPanel.addConsoleLogLine(localizer.getLocalText(bre.errMsgkey));
            serverPanel.addConsoleLogLine(bre.resp.getMsg());

            UiUtil.showErrorDialog("Server " + ctlr.name + " " + localizer.getLocalText(bre.errMsgkey),
                    bre.resp.getMsg());
        }
        else if(rval != null && exc != null) {
            System.out.println(ctlr.name + " SetNbrsConfigPropertyWorker exception from proxy: ");
            exc.printStackTrace();

            serverPanel.addConsoleLogLine(localizer.getLocalText("iccrApiException"));
            serverPanel.addConsoleLogLine(exc.getLocalizedMessage());

            UiUtil.showErrorDialog("Server " + ctlr.name + " " + localizer.getLocalText("iccrSetNbrsConfigPropertyError"),
                    localizer.getLocalText("iccrApiException") + ": " + exc.getLocalizedMessage());
        }
        else if(rval == null) {
            System.out.println(ctlr.name + " SetNbrsConfigPropertyWorker no exceptions");
            serverPanel.neighborPanel.save.setEnabled(false);
        }
    }


}
