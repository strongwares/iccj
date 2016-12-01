package org.iotacontrolcenter.ui.panel;


import org.iotacontrolcenter.ui.app.Constants;
import org.iotacontrolcenter.ui.controller.ServerController;
import org.iotacontrolcenter.ui.dialog.SpringUtilities;
import org.iotacontrolcenter.ui.properties.locale.Localizer;

import javax.swing.*;
import java.awt.*;

public class ServerActionPanel extends JPanel {

    private ServerController ctlr;
    private JButton deleteIotaDb;
    private JButton deleteIota;
    private JButton installIota;
    private JPanel internalPanel;
    private Localizer localizer;
    private JButton settings;
    private JButton startIota;
    private JButton startWallet;
    private JButton stopIota;

    public ServerActionPanel(Localizer localizer, ServerController ctlr) {
        super();
        this.localizer = localizer;
        this.ctlr = ctlr;
        init();
    }

    private void init() {
        //setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        internalPanel = new JPanel(new SpringLayout());
        internalPanel.setBackground(Color.lightGray);
        internalPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        //internalPanel.setLayout(new BoxLayout(internalPanel, BoxLayout.PAGE_AXIS));

        settings = new JButton(localizer.getLocalText("buttonLabelSettings"));
        settings.setAlignmentX(Component.CENTER_ALIGNMENT);
        settings.setActionCommand(Constants.SERVER_ACTION_SETTINGS);
        settings.addActionListener(ctlr);
        internalPanel.add(settings);

        //internalPanel.add(Box.createRigidArea(new Dimension(20, 10)));

        installIota = new JButton(localizer.getLocalText("buttonLabelInstallIota"));
        installIota.setAlignmentX(Component.CENTER_ALIGNMENT);
        installIota.setActionCommand(Constants.SERVER_ACTION_INSTALL_IOTA);
        installIota.addActionListener(ctlr);
        internalPanel.add(installIota);

        //internalPanel.add(Box.createRigidArea(new Dimension(20, 10)));

        startIota = new JButton(localizer.getLocalText("buttonLabelStartIota"));
        startIota.setAlignmentX(Component.CENTER_ALIGNMENT);
        startIota.setActionCommand(Constants.SERVER_ACTION_START_IOTA);
        startIota.addActionListener(ctlr);
        internalPanel.add(startIota);

        //internalPanel.add(Box.createRigidArea(new Dimension(20, 10)));

        stopIota = new JButton(localizer.getLocalText("buttonLabelStopIota"));
        stopIota.setAlignmentX(Component.CENTER_ALIGNMENT);
        stopIota.setActionCommand(Constants.SERVER_ACTION_STOP_IOTA);
        stopIota.addActionListener(ctlr);
        internalPanel.add(stopIota);

        //internalPanel.add(Box.createRigidArea(new Dimension(20, 10)));

        startWallet = new JButton(localizer.getLocalText("buttonLabelStartWallet"));
        startWallet.setAlignmentX(Component.CENTER_ALIGNMENT);
        startWallet.setActionCommand(Constants.SERVER_ACTION_START_WALLET);
        startWallet.addActionListener(ctlr);
        internalPanel.add(startWallet);

        //internalPanel.add(Box.createRigidArea(new Dimension(20, 10)));

        deleteIotaDb = new JButton(localizer.getLocalText("buttonLabelDeleteIotaDb"));
        deleteIotaDb.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteIotaDb.setActionCommand(Constants.SERVER_ACTION_DELETEDB_IOTA);
        deleteIotaDb.addActionListener(ctlr);
        internalPanel.add(deleteIotaDb);

        //internalPanel.add(Box.createRigidArea(new Dimension(20, 10)));

        deleteIota = new JButton(localizer.getLocalText("buttonLabelUninstallIota"));
        deleteIota.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteIota.setActionCommand(Constants.SERVER_ACTION_UNINSTALL_IOTA);
        deleteIota.addActionListener(ctlr);
        internalPanel.add(deleteIota);

        SpringUtilities.makeCompactGrid(internalPanel,
                7, 1, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        add(internalPanel);
        add(Box.createVerticalGlue());
    }
}
