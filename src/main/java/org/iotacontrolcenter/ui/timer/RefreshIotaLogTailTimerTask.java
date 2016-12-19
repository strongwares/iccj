package org.iotacontrolcenter.ui.timer;

import org.iotacontrolcenter.ui.app.Constants;
import org.iotacontrolcenter.ui.controller.ServerController;

import java.util.TimerTask;

public class RefreshIotaLogTailTimerTask extends TimerTask {

    public ServerController ctlr;

    public RefreshIotaLogTailTimerTask(ServerController ctlr) {
        super();
        this.ctlr = ctlr;
    }

    @Override
    public void run() {
        ctlr.getIotaLogUpdate(Constants.IOTA_LOG_QP_DIRECTION_TAIL);
    }
}
