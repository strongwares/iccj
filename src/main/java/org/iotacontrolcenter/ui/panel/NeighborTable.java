package org.iotacontrolcenter.ui.panel;

import org.iotacontrolcenter.ui.controller.ServerController;
import org.iotacontrolcenter.ui.model.NeighborModel;

import javax.swing.*;

public class NeighborTable extends JTable {

    private static final int METRIC_COL_WIDTH = 40;

    private NeighborModel neighborModel;
    private ServerController ctlr;

    public NeighborTable(NeighborModel neighborModel, ServerController ctlr) {
        super(neighborModel);
        this.neighborModel = neighborModel;
        this.ctlr = ctlr;
        init();
    }

    private void init() {
        for(int col = 0; col < 3; col++ ) {
            getColumnModel().getColumn(col).setPreferredWidth(METRIC_COL_WIDTH);
            getColumnModel().getColumn(col).setMinWidth(METRIC_COL_WIDTH);
            getColumnModel().getColumn(col).setMaxWidth(METRIC_COL_WIDTH);
        }

        getColumnModel().getColumn(3).setPreferredWidth(53);
        getColumnModel().getColumn(3).setMinWidth(53);
        getColumnModel().getColumn(3).setMaxWidth(53);

        getColumnModel().getColumn(4).setPreferredWidth(175);
        getColumnModel().getColumn(4).setMaxWidth(175);
        //getColumnModel().getColumn(2).setPreferredWidth(190);
        setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    }
}
