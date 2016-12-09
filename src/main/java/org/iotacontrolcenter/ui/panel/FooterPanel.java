package org.iotacontrolcenter.ui.panel;


import org.iotacontrolcenter.dto.IotaGetNodeInfoResponseDto;
import org.iotacontrolcenter.ui.controller.ServerController;
import org.iotacontrolcenter.ui.properties.locale.Localizer;

import javax.swing.*;
import java.awt.*;

public class FooterPanel extends JPanel {

    private ServerController ctlr;
    private Localizer localizer;
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
    }

    public void dataUpdate(IotaGetNodeInfoResponseDto nodeInfo) {
        milestonesLabel.setText(milestonesBase + nodeInfo.getLatestMilestoneIndex());
        solidMilestonesLabel.setText(solidMilestonesBase + nodeInfo.getLatestSolidSubtangleMilestoneIndex());
        //seenTransactionsLabel.setText(seenTransactionsBase + nodeInfo.getTransactionsToRequest());
    }

    private void init() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setPreferredSize(new Dimension(900, 50));

        onLineLabel = new JLabel(localizer.getLocalText("labelTextFooterOnline") + ":");
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

        seenTransactionsBase = localizer.getLocalText("labelTextFooterSeenTransactions") + ": ";
        seenTransactionsLabel = new JLabel(seenTransactionsBase);
        add(seenTransactionsLabel);
    }
}
