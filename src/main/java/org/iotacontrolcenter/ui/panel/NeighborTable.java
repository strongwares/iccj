package org.iotacontrolcenter.ui.panel;

import org.iotacontrolcenter.ui.controller.ServerController;
import org.iotacontrolcenter.ui.model.NeighborModel;

import javax.swing.*;

public class NeighborTable extends JTable {

    private NeighborModel neighborModel;
    private ServerController ctlr;

    public NeighborTable(NeighborModel neighborModel, ServerController ctlr) {
        super(neighborModel);
        this.neighborModel = neighborModel;
        this.ctlr = ctlr;
    }
}
