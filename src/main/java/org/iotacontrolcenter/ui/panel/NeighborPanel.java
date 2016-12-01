package org.iotacontrolcenter.ui.panel;


import org.iotacontrolcenter.ui.app.Constants;
import org.iotacontrolcenter.ui.controller.ServerController;
import org.iotacontrolcenter.ui.model.NeighborModel;
import org.iotacontrolcenter.ui.properties.locale.Localizer;

import javax.swing.*;
import java.awt.*;

public class NeighborPanel extends JPanel {

    private Localizer localizer;
    private ServerController ctlr;
    private NeighborModel neighborModel;
    private NeighborTable neighborTable;
    private JButton save;

    public NeighborPanel(Localizer localizer, ServerController ctlr) {
        super();
        this.localizer = localizer;
        this.ctlr = ctlr;
        init();
    }

    private void init() {
        //setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createRaisedBevelBorder()));

        neighborModel = new NeighborModel(localizer);

        neighborTable = new NeighborTable(neighborModel, ctlr);
        neighborTable.setPreferredScrollableViewportSize(new Dimension(400, 400));
        neighborTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(neighborTable);
        scrollPane.setBackground(Color.white);
        scrollPane.getViewport().setBackground(Color.white);
        scrollPane.getVerticalScrollBar().setUnitIncrement(50);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        buttonPanel.add(Box.createHorizontalGlue());

        save = new JButton(localizer.getLocalText("buttonLabelSaveChanges"));
        save.setActionCommand(Constants.NEIGHBOR_PANEL_SAVE_CHANGES);
        save.addActionListener(ctlr);
        buttonPanel.add(save);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
