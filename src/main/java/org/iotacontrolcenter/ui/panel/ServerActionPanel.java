package org.iotacontrolcenter.ui.panel;


import org.iotacontrolcenter.ui.app.Constants;
import org.iotacontrolcenter.ui.controller.ServerController;
import org.iotacontrolcenter.ui.properties.locale.Localizer;

import javax.swing.*;
import java.awt.*;

public class ServerActionPanel extends JPanel {

    private ServerController ctlr;
    private JButton deleteIotaDb;
    private JButton deleteIota;
    private JButton installIota;
    private Localizer localizer;
    private JButton settings;
    private JButton startIota;
    private JButton stopIota;

    public ServerActionPanel(Localizer localizer, ServerController ctlr) {
        super();
        this.localizer = localizer;
        this.ctlr = ctlr;
        init();
    }

    private void init() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        settings = new JButton(localizer.getLocalText("buttonLabelSettings"));
        settings.setAlignmentX(Component.CENTER_ALIGNMENT);
        settings.setActionCommand(Constants.SERVER_ACTION_SETTINGS);
        settings.addActionListener(ctlr);
        add(settings);

        installIota = new JButton(localizer.getLocalText("buttonLabelInstallIota"));
        installIota.setAlignmentX(Component.CENTER_ALIGNMENT);
        installIota.setActionCommand(Constants.SERVER_ACTION_INSTALL_IOTA);
        installIota.addActionListener(ctlr);
        add(installIota);

        startIota = new JButton(localizer.getLocalText("buttonLabelStartIota"));
        startIota.setAlignmentX(Component.CENTER_ALIGNMENT);
        startIota.setActionCommand(Constants.SERVER_ACTION_START_IOTA);
        startIota.addActionListener(ctlr);
        add(startIota);

        stopIota = new JButton(localizer.getLocalText("buttonLabelStopIota"));
        stopIota.setAlignmentX(Component.CENTER_ALIGNMENT);
        stopIota.setActionCommand(Constants.SERVER_ACTION_STOP_IOTA);
        stopIota.addActionListener(ctlr);
        add(stopIota);

        deleteIotaDb = new JButton(localizer.getLocalText("buttonLabelDeleteIotaDb"));
        deleteIotaDb.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteIotaDb.setActionCommand(Constants.SERVER_ACTION_DELETEDB_IOTA);
        deleteIotaDb.addActionListener(ctlr);
        add(deleteIotaDb);

        deleteIota = new JButton(localizer.getLocalText("buttonLabelUninstallIota"));
        deleteIota.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteIota.setActionCommand(Constants.SERVER_ACTION_UNINSTALL_IOTA);
        deleteIota.addActionListener(ctlr);
        add(deleteIota);
    }
}
