package org.iotacontrolcenter.ui.panel;


import org.iotacontrolcenter.ui.controller.ServerController;
import org.iotacontrolcenter.ui.properties.locale.Localizer;

import javax.swing.*;
import java.awt.*;

public class FooterPanel extends JPanel {

    private ServerController ctlr;
    private Localizer localizer;
    public JLabel onLineLabel;
    public JLabel milestonesLabel;
    public JLabel solidMilestonesLabel;
    public JLabel seenTransactionsLabel;

    public FooterPanel(Localizer localizer, ServerController ctlr) {
        super();
        this.localizer = localizer;
        this.ctlr = ctlr;
        init();
    }

    private void init() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setPreferredSize(new Dimension(900, 50));

        onLineLabel = new JLabel(localizer.getLocalText("labelTextFooterOnline") + ":");
        add(onLineLabel);

        add(Box.createHorizontalGlue());

        milestonesLabel = new JLabel(localizer.getLocalText("labelTextFooterMilestones") + ":");
        add(milestonesLabel);

        add(Box.createRigidArea(new Dimension(100, 20)));

        solidMilestonesLabel = new JLabel(localizer.getLocalText("labelTextFooterSolidMilestones") + ":");
        add(solidMilestonesLabel);

        add(Box.createHorizontalGlue());

        seenTransactionsLabel = new JLabel(localizer.getLocalText("labelTextFooterSeenTransactions") + ":");
        add(seenTransactionsLabel);
    }
}
