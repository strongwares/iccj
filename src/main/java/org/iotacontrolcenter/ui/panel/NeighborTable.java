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
        init();
    }

    private void init() {
        getColumnModel().getColumn(0).setPreferredWidth(53);
        getColumnModel().getColumn(0).setMinWidth(53);
        getColumnModel().getColumn(0).setMaxWidth(53);
        getColumnModel().getColumn(1).setPreferredWidth(175);
        getColumnModel().getColumn(1).setMaxWidth(175);
        //getColumnModel().getColumn(2).setPreferredWidth(190);
        setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    }
}
