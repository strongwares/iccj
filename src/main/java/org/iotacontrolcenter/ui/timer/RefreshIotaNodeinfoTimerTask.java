package org.iotacontrolcenter.ui.timer;


import org.iotacontrolcenter.ui.controller.ServerController;

import java.util.TimerTask;

public class RefreshIotaNodeinfoTimerTask extends TimerTask {

    public ServerController ctlr;

    public RefreshIotaNodeinfoTimerTask(ServerController ctlr) {
        super();
        this.ctlr = ctlr;
    }

    @Override
    public void run() {
        if(ctlr.iotaActive && ctlr.isConnected) {
            ctlr.serverActionGetIotaNodeinfo();
        }
    }
}
