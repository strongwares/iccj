package org.iotacontrolcenter.ui.controller.worker;

import org.iotacontrolcenter.ui.controller.ServerController;
import org.iotacontrolcenter.ui.panel.ServerPanel;
import org.iotacontrolcenter.ui.properties.locale.Localizer;
import org.iotacontrolcenter.ui.proxy.ServerProxy;

import javax.swing.*;

public abstract class ObjectAbstractApiWorker extends SwingWorker<Object, Void> {

    public Localizer localizer;
    public ServerPanel serverPanel;
    public ServerProxy proxy;
    public ServerController ctlr;

    public ObjectAbstractApiWorker(Localizer localizer, ServerPanel serverPanel,
                                   ServerProxy proxy, ServerController ctlr) {
        super();
        this.localizer = localizer;
        this.serverPanel = serverPanel;
        this.proxy = proxy;
        this.ctlr = ctlr;

    }
}
