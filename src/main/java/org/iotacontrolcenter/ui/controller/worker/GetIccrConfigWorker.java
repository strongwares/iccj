package org.iotacontrolcenter.ui.controller.worker;

import org.iotacontrolcenter.ui.controller.ServerController;
import org.iotacontrolcenter.ui.panel.ServerPanel;
import org.iotacontrolcenter.ui.properties.locale.Localizer;
import org.iotacontrolcenter.ui.proxy.BadResponseException;
import org.iotacontrolcenter.ui.proxy.ServerProxy;
import org.iotacontrolcenter.ui.util.UiUtil;

import java.util.Properties;

public class GetIccrConfigWorker extends ObjectAbstractApiWorker {

    protected Properties iccrProps;

    public GetIccrConfigWorker(Localizer localizer, ServerPanel serverPanel, ServerProxy proxy,
                               ServerController ctlr) {
        super(localizer, serverPanel, proxy, ctlr);
    }

    @Override
    public Object doInBackground() {
        try {
            return proxy.iccrGetConfig();
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
        Object rval = null;
        try {
            rval = get();
        }
        catch(Exception e) {
            System.out.println(ctlr.name + " GetIccrConfigWorker done: exception from get: ");
            e.printStackTrace();
        }

        if(rval == null) {
            System.out.println(ctlr.name + " GetIccrConfigWorker done: return value is empty");
            return;
        }

        BadResponseException bre = null;
        Exception exc =  null;
        iccrProps = null;

        if(rval instanceof BadResponseException) {
            bre = (BadResponseException)rval;
        }
        else if(rval instanceof Exception) {
            exc = (Exception)rval;
        }
        else if(rval instanceof  Properties) {
            iccrProps = (Properties)rval;
        }
        else {
            System.out.println(ctlr.name + " GetIccrConfigWorker unexpected return type: " + rval.getClass().getCanonicalName());
            return;
        }

        ctlr.logIsConnected(bre == null && exc == null);

        if (bre != null) {
            System.out.println(ctlr.name + " GetIccrConfigWorker done: bad response: " + bre.errMsgkey +
                    ", " + bre.resp.getMsg());

            serverPanel.addConsoleLogLine(localizer.getLocalText(bre.errMsgkey));
            serverPanel.addConsoleLogLine(bre.resp.getMsg());

            UiUtil.showErrorDialog("Server " + ctlr.name + " " + localizer.getLocalText(bre.errMsgkey),
                    bre.resp.getMsg());
        }
        else if (exc != null) {
            System.out.println(ctlr.name + " GetIccrConfigWorker done: exception from proxy: ");
            exc.printStackTrace();

            serverPanel.addConsoleLogLine(localizer.getLocalText("iccrGetConfigError"));
            serverPanel.addConsoleLogLine(exc.getLocalizedMessage());

            UiUtil.showErrorDialog(localizer.getLocalText("iccrGetConfigError"),
                    localizer.getLocalText("iccrApiException") + ": " +
                            exc.getLocalizedMessage());

        }
        else {
            System.out.println(ctlr.name + " GetIccrConfigWorker done: no exceptions");
            ctlr.iccrProps = iccrProps;
        }
    }

}
