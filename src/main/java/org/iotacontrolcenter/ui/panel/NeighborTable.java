package org.iotacontrolcenter.ui.panel;

import org.iotacontrolcenter.ui.controller.ServerController;
import org.iotacontrolcenter.ui.model.NeighborModel;
import org.iotacontrolcenter.ui.properties.locale.Localizer;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.event.MouseEvent;

public class NeighborTable extends JTable {

    private static final int METRIC_COL_WIDTH = 45;

    private ServerController ctlr;
    private Localizer localizer;
    private NeighborModel neighborModel;

    protected String[] columnToolTips = {
            "AT",
            "IT",
            "NT",
            "Active",
            "Nbr",
            "Description"
    };

    public NeighborTable(Localizer localizer, NeighborModel neighborModel, ServerController ctlr) {
        super(neighborModel);
        this.localizer = localizer;
        this.neighborModel = neighborModel;
        this.ctlr = ctlr;
        init();
    }

    private void init() {
        columnToolTips[0] = localizer.getLocalText("neighborTableColumnTitleATTooltip");
        columnToolTips[1] = localizer.getLocalText("neighborTableColumnTitleITTooltip");
        columnToolTips[2] = localizer.getLocalText("neighborTableColumnTitleNTTooltip");
        columnToolTips[3] = localizer.getLocalText("neighborTableColumnTitleStatusTooltip");
        columnToolTips[4] = localizer.getLocalText("neighborTableColumnTitleNeighborTooltip");
        columnToolTips[5] = localizer.getLocalText("neighborTableColumnTitleDescriptionTooltip");

        for(int col = 0; col < 3; col++ ) {
            getColumnModel().getColumn(col).setPreferredWidth(METRIC_COL_WIDTH);
            getColumnModel().getColumn(col).setMinWidth(METRIC_COL_WIDTH);
            //getColumnModel().getColumn(col).setMaxWidth(METRIC_COL_WIDTH);
        }

        getColumnModel().getColumn(3).setPreferredWidth(53);
        getColumnModel().getColumn(3).setMinWidth(53);
        getColumnModel().getColumn(3).setMaxWidth(53);

        getColumnModel().getColumn(4).setPreferredWidth(175);
        //getColumnModel().getColumn(4).setMaxWidth(175);
        setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    }

    protected JTableHeader createDefaultTableHeader() {
        return new JTableHeader(columnModel) {
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int index = columnModel.getColumnIndexAtX(p.x);
                int realIndex = columnModel.getColumn(index).getModelIndex();
                return columnToolTips[realIndex];
            }
        };
    }
}
