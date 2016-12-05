package org.iotacontrolcenter.ui.timer;

import org.iotacontrolcenter.ui.controller.ServerController;

import java.util.TimerTask;

public class RefreshIotaNeighborTimerTask extends TimerTask {

    public ServerController ctlr;

    public RefreshIotaNeighborTimerTask(ServerController ctlr) {
        super();
        this.ctlr = ctlr;
    }

    @Override
    public void run() {
        ctlr.serverActionGetIotaNeighbors();
    }
}
