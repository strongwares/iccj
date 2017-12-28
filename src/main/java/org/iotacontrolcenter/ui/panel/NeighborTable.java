package org.iotacontrolcenter.ui.panel;

import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import org.iotacontrolcenter.ui.controller.ServerController;
import org.iotacontrolcenter.ui.model.NeighborModel;
import org.iotacontrolcenter.ui.properties.locale.Localizer;

public class NeighborTable extends JTable {


    private static final long serialVersionUID = -1864468307325256081L;

    private static final int METRIC_COL_WIDTH = 45;

    @SuppressWarnings("unused")
    private ServerController ctlr;
    private Localizer        localizer;
    @SuppressWarnings("unused")
    private NeighborModel    neighborModel;

    protected String[] columnToolTips = {
            "AT",
            "IT",
            "NT",
            "24h %", "7d %",
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
        String[] localizerKeys = {
            "neighborTableColumnTitleATTooltip",
            "neighborTableColumnTitleITTooltip",
            "neighborTableColumnTitleNTTooltip",
            "neighborTableColumnTitleDayStatsTooltip",
            "neighborTableColumnTitleWeekStatsTooltip",
            "neighborTableColumnTitleStatusTooltip",
            "neighborTableColumnTitleNeighborTooltip",
            "neighborTableColumnTitleDescriptionTooltip"
        };
        
        for (int col = 0; col < columnToolTips.length; col++) {
            columnToolTips[col] = localizer.getLocalText(localizerKeys[col]);
        }

        int addressColId = 6;
        for (int col = 0; col < addressColId; col++) {
            getColumnModel().getColumn(col).setPreferredWidth(METRIC_COL_WIDTH);
            getColumnModel().getColumn(col).setMinWidth(METRIC_COL_WIDTH);
            // getColumnModel().getColumn(col).setMaxWidth(METRIC_COL_WIDTH);
        }

        getColumnModel().getColumn(addressColId).setPreferredWidth(53);
        getColumnModel().getColumn(addressColId).setMinWidth(175);
        // getColumnModel().getColumn(addressColId).setMaxWidth(53);

        getColumnModel().getColumn(addressColId + 1).setPreferredWidth(175);
        // getColumnModel().getColumn(4).setMaxWidth(175);
        setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    }

    protected JTableHeader createDefaultTableHeader() {
        return new JTableHeader(columnModel) {

            private static final long serialVersionUID = 8953171382075962395L;

            public String getToolTipText(MouseEvent e) {
                java.awt.Point p = e.getPoint();
                int index = columnModel.getColumnIndexAtX(p.x);
                int realIndex = columnModel.getColumn(index).getModelIndex();
                return columnToolTips[realIndex];
            }
        };
    }
}
