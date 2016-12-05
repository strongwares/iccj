package org.iotacontrolcenter.ui.controller.worker;


import org.iotacontrolcenter.ui.controller.ServerController;
import org.iotacontrolcenter.ui.dialog.ServerSettingsDialog;
import org.iotacontrolcenter.ui.panel.ServerPanel;
import org.iotacontrolcenter.ui.properties.locale.Localizer;
import org.iotacontrolcenter.ui.proxy.ServerProxy;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ShowServerSettingsDialogWorker extends GetIccrConfigWorker {

    public ShowServerSettingsDialogWorker(Localizer localizer, ServerPanel serverPanel, ServerProxy proxy,
                                          ServerController ctlr) {
        super(localizer, serverPanel, proxy, ctlr);
    }

    @Override
    protected void done() {
        super.done();

        if (iccrProps == null) {
            // The parent GetIccrConfigWorker will set this:
            //ctlr.logIsConnected(false);
            //System.out.println(ctlr.name + " ShowServerSettingsDialogWorker done: iccrProps not set");
            return;
        }

        ctlr.serverSettingsDialog = new ServerSettingsDialog(localizer, ctlr, iccrProps);

        ctlr.serverSettingsDialog.setLocationRelativeTo(serverPanel);

        ctlr.serverSettingsDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                ctlr.serverSettingsDialog = null;
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ctlr.serverSettingsDialog.iccrPortTextField.requestFocusInWindow();
            }
        });

        ctlr.serverSettingsDialog.setVisible(true);
    }
}
