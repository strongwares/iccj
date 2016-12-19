package org.iotacontrolcenter.ui.controller.worker;

import org.iotacontrolcenter.ui.controller.ServerController;

import javax.swing.*;

public abstract class AbstractSwingWorker extends SwingWorker<Void, Void> {

    protected ServerController ctlr;
    public AbstractSwingWorker(ServerController ctlr) {
        this.ctlr = ctlr;
    }

    @Override
    public Void doInBackground() {
        return runIt();
    }

    public abstract Void runIt();

}
