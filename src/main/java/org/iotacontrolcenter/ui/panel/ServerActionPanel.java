package org.iotacontrolcenter.ui.panel;


import org.iotacontrolcenter.ui.app.Constants;
import org.iotacontrolcenter.ui.controller.ServerController;
import org.iotacontrolcenter.ui.dialog.SpringUtilities;
import org.iotacontrolcenter.ui.properties.locale.Localizer;
import org.iotacontrolcenter.ui.util.UiUtil;

import javax.swing.*;
import java.awt.*;

public class ServerActionPanel extends JPanel {

    private static final long serialVersionUID = 5504842877319309913L;
    private ServerController ctlr;
    private JButton deleteIotaDb;
    private JButton deleteIota;
    private JButton eventLog;
    public JButton installIota;
    private JPanel internalPanel;
    private JButton iotaLog;
    private Localizer localizer;
    private JButton settings;
    private JButton startIota;
    @SuppressWarnings("unused")
    private JButton startWallet;
    private JButton stopIota;

    public ServerActionPanel(Localizer localizer, ServerController ctlr) {
        super();
        this.localizer = localizer;
        this.ctlr = ctlr;
        init();
    }

    private void init() {
        internalPanel = new JPanel(new SpringLayout());
        internalPanel.setBackground(Color.lightGray);
        internalPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        settings = new JButton(localizer.getLocalText("buttonLabelSettings") + "...");
        settings.setToolTipText(localizer.getLocalText("buttonLabelSettingsTooltip"));
        settings.setAlignmentX(Component.CENTER_ALIGNMENT);
        settings.setActionCommand(Constants.SERVER_ACTION_SETTINGS);
        settings.addActionListener(ctlr);
        internalPanel.add(settings);

        installIota = new JButton(localizer.getLocalText("buttonLabelInstallIota"));
        installIota.setToolTipText(localizer.getLocalText("buttonLabelInstallIotaTooltip"));
        installIota.setBackground(Color.darkGray);
        installIota.setForeground(Color.white);
        installIota.setAlignmentX(Component.CENTER_ALIGNMENT);
        installIota.setActionCommand(Constants.SERVER_ACTION_INSTALL_IOTA);
        installIota.addActionListener(ctlr);
        internalPanel.add(installIota);

        startIota = new JButton(localizer.getLocalText("buttonLabelStartIota"));
        startIota.setToolTipText(localizer.getLocalText("buttonLabelStartIotaTooltip"));
        startIota.setBackground(new Color(0, 100, 0));
        startIota.setForeground(Color.white);
        startIota.setAlignmentX(Component.CENTER_ALIGNMENT);
        startIota.setActionCommand(Constants.SERVER_ACTION_START_IOTA);
        startIota.addActionListener(ctlr);
        internalPanel.add(startIota);

        stopIota = new JButton(localizer.getLocalText("buttonLabelStopIota"));
        stopIota.setToolTipText(localizer.getLocalText("buttonLabelStopIotaTooltip"));
        stopIota.setBackground(Color.red);
        stopIota.setForeground(Color.white);
        stopIota.setAlignmentX(Component.CENTER_ALIGNMENT);
        stopIota.setActionCommand(Constants.SERVER_ACTION_STOP_IOTA);
        stopIota.addActionListener(ctlr);
        internalPanel.add(stopIota);

        /*
        startWallet = new JButton(localizer.getLocalText("buttonLabelStartWallet"));
        startWallet.setToolTipText(localizer.getLocalText("buttonLabelStartWalletTooltip"));
        startWallet.setBackground(Color.blue);
        startWallet.setForeground(Color.white);
        startWallet.setAlignmentX(Component.CENTER_ALIGNMENT);
        startWallet.setActionCommand(Constants.SERVER_ACTION_START_WALLET);
        startWallet.addActionListener(ctlr);
        internalPanel.add(startWallet);
        */

        deleteIotaDb = new JButton(localizer.getLocalText("buttonLabelDeleteIotaDb"));
        deleteIotaDb.setToolTipText(localizer.getLocalText("buttonLabelDeleteIotaDbTooltip"));
        deleteIotaDb.setBackground(Color.darkGray);
        deleteIotaDb.setForeground(Color.white);
        deleteIotaDb.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteIotaDb.setActionCommand(Constants.SERVER_ACTION_DELETEDB_IOTA);
        deleteIotaDb.addActionListener(ctlr);
        internalPanel.add(deleteIotaDb);

        deleteIota = new JButton(localizer.getLocalText("buttonLabelUninstallIota"));
        deleteIota.setToolTipText(localizer.getLocalText("buttonLabelUninstallIotaTooltip"));
        deleteIota.setBackground(Color.darkGray);
        deleteIota.setForeground(Color.white);
        deleteIota.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteIota.setActionCommand(Constants.SERVER_ACTION_UNINSTALL_IOTA);
        deleteIota.addActionListener(ctlr);
        internalPanel.add(deleteIota);

        eventLog = new JButton(localizer.getLocalText("buttonLabelIccrEventLog") + "...");
        eventLog.setToolTipText(localizer.getLocalText("buttonLabelIccrEventLogTooltip"));
        eventLog.setAlignmentX(Component.CENTER_ALIGNMENT);
        eventLog.setActionCommand(Constants.SERVER_ACTION_ICCR_EVENTLOG);
        eventLog.addActionListener(ctlr);
        internalPanel.add(eventLog);

        iotaLog = new JButton(localizer.getLocalText("buttonLabelIotaLog") + "...");
        iotaLog.setToolTipText(localizer.getLocalText("buttonLabelIotaLogTooltip"));
        iotaLog.setAlignmentX(Component.CENTER_ALIGNMENT);
        iotaLog.setActionCommand(Constants.SERVER_ACTION_IOTA_LOG);
        iotaLog.addActionListener(ctlr);
        internalPanel.add(iotaLog);

        SpringUtilities.makeCompactGrid(internalPanel,
                8, 1, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        add(internalPanel);
        add(Box.createVerticalGlue());
    }

    public void setDownloadActive(boolean isActive) {
        installIota.setEnabled(!isActive);
        if(isActive) {
            installIota.setIcon(UiUtil.loadIcon(Constants.IMAGE_ICON_FILENAME_BLUE_LOADING));
        }
        else {
            installIota.setIcon(null);
        }
    }
}
