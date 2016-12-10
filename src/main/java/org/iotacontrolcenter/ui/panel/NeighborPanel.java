package org.iotacontrolcenter.ui.panel;


import org.iotacontrolcenter.dto.IccrIotaNeighborsPropertyDto;
import org.iotacontrolcenter.dto.NeighborDto;
import org.iotacontrolcenter.ui.app.Constants;
import org.iotacontrolcenter.ui.controller.ServerController;
import org.iotacontrolcenter.ui.model.NeighborModel;
import org.iotacontrolcenter.ui.properties.locale.Localizer;

import javax.swing.*;
import java.awt.*;

public class NeighborPanel extends JPanel {

    private JButton add;
    private Localizer localizer;
    private ServerController ctlr;
    public NeighborModel neighborModel;
    public NeighborTable neighborTable;
    private JButton remove;
    public JButton save;

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
        neighborModel.addTableModelListener(ctlr);

        neighborTable = new NeighborTable(localizer, neighborModel, ctlr);
        neighborTable.setPreferredScrollableViewportSize(new Dimension(500, 450));
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

        remove = new JButton(localizer.getLocalText("buttonLabelRemoveSelectedNbr"));
        remove.setToolTipText(localizer.getLocalText("buttonLabelRemoveSelectedNbrTooltip"));
        remove.setActionCommand(Constants.NEIGHBOR_PANEL_REMOVE_SELECTED);
        remove.addActionListener(ctlr);
        buttonPanel.add(remove);

        buttonPanel.add(Box.createHorizontalGlue());

        add = new JButton(localizer.getLocalText("buttonLabelAddNewNbr"));
        add.setToolTipText(localizer.getLocalText("buttonLabelAddNewNbrTooltip"));
        add.setActionCommand(Constants.NEIGHBOR_PANEL_ADD_NEW);
        add.addActionListener(ctlr);
        buttonPanel.add(add);

        save = new JButton(localizer.getLocalText("buttonLabelSaveChanges"));
        save.setActionCommand(Constants.NEIGHBOR_PANEL_SAVE_CHANGES);
        save.addActionListener(ctlr);
        save.setEnabled(false);
        buttonPanel.add(save);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setNeighbors(IccrIotaNeighborsPropertyDto dto) {
        System.out.println("setNeighbors:");
        for(NeighborDto nbr : dto.getNbrs()) {
            System.out.println(dto.toString());
            neighborModel.addNeighbor(nbr);
        }
    }
}
