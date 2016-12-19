package org.iotacontrolcenter.ui.panel;


import org.iotacontrolcenter.dto.IotaGetNodeInfoResponseDto;
import org.iotacontrolcenter.ui.app.Constants;
import org.iotacontrolcenter.ui.controller.ServerController;
import org.iotacontrolcenter.ui.properties.locale.Localizer;
import org.iotacontrolcenter.ui.util.UiUtil;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class FooterPanel extends JPanel implements PropertyChangeListener {

    private ServerController ctlr;
    private Localizer localizer;
    private ImageIcon offLineIcon;
    private ImageIcon onLineIcon;
    private ImageIcon unknownIcon;
    public JLabel onLineLabel;
    public String milestonesBase;
    public JLabel milestonesLabel;
    public String solidMilestonesBase;
    public JLabel solidMilestonesLabel;
    public String seenTransactionsBase;
    public JLabel seenTransactionsLabel;

    public FooterPanel(Localizer localizer, ServerController ctlr) {
        super();
        this.localizer = localizer;
        this.ctlr = ctlr;
        init();
        ctlr.addPropertyChangeListener(this);
    }

    public void propertyChange(PropertyChangeEvent e) {
        if(e == null) {
            return;
        }
        if(e.getPropertyName().equals(Constants.IS_CONNECTED_EVENT)) {
            setIsConnected((Boolean)e.getNewValue());
        }
        else if(e.getPropertyName().equals(Constants.IS_CONNECTED_UNKNOWN_EVENT)) {
            setConnectionUnknown();
        }
    }

    public void dataUpdate(IotaGetNodeInfoResponseDto nodeInfo) {
        milestonesLabel.setText(milestonesBase + nodeInfo.getLatestMilestoneIndex());
        solidMilestonesLabel.setText(solidMilestonesBase + nodeInfo.getLatestSolidSubtangleMilestoneIndex());
        //seenTransactionsLabel.setText(seenTransactionsBase + nodeInfo.getTransactionsToRequest());
    }

    private void setConnectionUnknown() {
        onLineLabel.setToolTipText(localizer.getLocalText("serverFooterIccrNotConnectedTooltip"));
        onLineLabel.setIcon(getUnknownIcon());
    }

    private void setIsConnected(boolean isConnected) {
        if(isConnected) {
            onLineLabel.setToolTipText(localizer.getLocalText("serverFooterIotaOnlineTooltip"));
            onLineLabel.setIcon(getOnlineIcon());
        }
        else {
            onLineLabel.setToolTipText(localizer.getLocalText("serverFooterIotaOfflineTooltip"));
            onLineLabel.setIcon(getOfflineIcon());
        }
    }

    private ImageIcon getOnlineIcon() {
        if(onLineIcon == null) {
            onLineIcon = UiUtil.loadIcon(Constants.IMAGE_ICON_FILENAME_SERVER_ONLINE);
        }
        return onLineIcon;
    }

    private ImageIcon getUnknownIcon() {
        if(unknownIcon == null) {
            unknownIcon = UiUtil.loadIcon(Constants.IMAGE_ICON_FILENAME_HELP);
        }
        return unknownIcon;
    }

    private ImageIcon getOfflineIcon() {
        if(offLineIcon == null) {
            offLineIcon = UiUtil.loadIcon(Constants.IMAGE_ICON_FILENAME_SERVER_OFFLINE);
        }
        return offLineIcon;
    }

    private void init() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setPreferredSize(new Dimension(900, 50));

        onLineLabel = new JLabel(localizer.getLocalText("labelTextFooterOnline") + ":", null, JLabel.CENTER);
        onLineLabel.setHorizontalTextPosition(JLabel.LEFT);
        //setIsConnected(false);
        setConnectionUnknown();
        add(onLineLabel);

        add(Box.createHorizontalGlue());

        milestonesBase = localizer.getLocalText("labelTextFooterMilestones") + ": ";
        milestonesLabel = new JLabel(milestonesBase);
        add(milestonesLabel);

        add(Box.createRigidArea(new Dimension(100, 20)));

        solidMilestonesBase = localizer.getLocalText("labelTextFooterSolidMilestones") + ": ";
        solidMilestonesLabel = new JLabel(solidMilestonesBase);
        add(solidMilestonesLabel);

        add(Box.createHorizontalGlue());

        /*
        seenTransactionsBase = localizer.getLocalText("labelTextFooterSeenTransactions") + ": ";
        seenTransactionsLabel = new JLabel(seenTransactionsBase);
        add(seenTransactionsLabel);
        */
    }
}
