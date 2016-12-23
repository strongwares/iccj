package org.iotacontrolcenter.ui.controller.worker;


import org.iotacontrolcenter.dto.ActionResponse;
import org.iotacontrolcenter.dto.IccrPropertyDto;
import org.iotacontrolcenter.dto.IccrPropertyListDto;
import org.iotacontrolcenter.ui.controller.ServerController;
import org.iotacontrolcenter.ui.panel.ServerPanel;
import org.iotacontrolcenter.ui.properties.locale.Localizer;
import org.iotacontrolcenter.ui.proxy.BadResponseException;
import org.iotacontrolcenter.ui.proxy.ServerProxy;

import javax.swing.*;

public abstract class ActionResponseAbstractApiWorker extends SwingWorker<Object, Void> {

    public String action;
    public IccrPropertyListDto actionProps;
    public ServerController ctlr;
    public Localizer localizer;
    public ServerPanel serverPanel;
    public ServerProxy proxy;

    public ActionResponseAbstractApiWorker(Localizer localizer,
                                           ServerPanel serverPanel,
                                           ServerProxy proxy,
                                           ServerController ctlr,
                                           String action,
                                           IccrPropertyListDto actionProps) {
        super();
        this.localizer = localizer;
        this.serverPanel = serverPanel;
        this.proxy = proxy;
        this.ctlr = ctlr;
        this.action = action;
        this.actionProps = actionProps;
    }

    @Override
    public Object doInBackground() {
        try {
            return doIt();
        }
        catch(BadResponseException bre) {
            return bre;
        }
        catch(Exception exc) {
            return exc;
        }
    }

    protected Object doIt() throws BadResponseException {
        return proxy.doIotaAction(action, actionProps);
    }

    protected String getActionStatusFromResponse(String key, ActionResponse ar) {
        String val = null;
        if(ar != null && ar.getProperties() !=  null) {
            for (IccrPropertyDto prop : ar.getProperties()) {
                //System.out.println(prop.getKey() + " -> " + prop.getValue());
                if (prop.getKey().equals(key)) {
                    val = prop.getValue();
                    break;
                }
            }
        }
        return val;
    }
}
