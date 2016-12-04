package org.iotacontrolcenter.ui.model;


import org.iotacontrolcenter.dto.NeighborDto;
import org.iotacontrolcenter.ui.properties.locale.Localizer;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class NeighborModel extends AbstractTableModel {

    private static final int STATUS_COL = 0;
    private static final int IP_COL = 1;
    private static final int DESCR_COL = 2;

    private String[] columnNames;
    public List<NeighborDto> nbrs = new ArrayList<>();
    private Localizer localizer;

    public NeighborModel(Localizer localizer) {
        super();
        this.localizer = localizer;
        init();
    }

    private void init()  {
        columnNames = new String[] {localizer.getLocalText("neighborTableColumnTitleStatus"),
                localizer.getLocalText("neighborTableColumnTitleNeighbor"),
                localizer.getLocalText("neighborTableColumnTitleDescription") };
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return nbrs.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public NeighborDto getRow(int row) {
        NeighborDto nbr = null;
        if(row <  getRowCount()) {
            nbr = nbrs.get(row);
        }
        return nbr;
    }

    public Object getValueAt(int row, int col) {
        NeighborDto nbr = nbrs.get(row);
        if(nbr == null) {
            return null;
        }
        if(col == STATUS_COL) {
            return nbr.isActive();
        }
        else if(col == IP_COL) {
            return nbr.toUri();
        }
        else if(col == DESCR_COL) {
            return nbr.getDescr();
        }
        return null;
    }

    public Class getColumnClass(int col) {
        if(col == STATUS_COL) {
            return Boolean.class;
        }
        else if(col == IP_COL) {
            return String.class;
        }
        else if(col == DESCR_COL) {
            return String.class;
        }
        return null;
    }

    public boolean isCellEditable(int row, int col) {
        return true;
    }

    public void addNeighbor(NeighborDto nbr) {
        if(!nbrs.contains(nbr)) {
            System.out.println("Adding new neighbor");
            nbrs.add(nbr);
        }
        fireTableDataChanged();
    }

    public void removeNeighbor(int row) {
        if(row <  getRowCount()) {
            nbrs.remove(row);
        }
        fireTableDataChanged();
    }

    public void setValueAt(Object value, int row, int col) {
        System.out.println("Setting value at " + row + "," + col
                    + " to " + value
                    + " (an instance of "
                    + value.getClass() + ")");

        NeighborDto nbr = getRow(row);
        if(nbr == null) {
            return;
        }

        try {
            if (col == STATUS_COL) {
                nbr.setActive((Boolean) value);
            } else if (col == IP_COL) {
                nbr.setUri((String) value);
            } else if (col == DESCR_COL) {
                nbr.setDescr((String) value);
            }
        }
        catch(Exception e) {
            System.out.println("Exception setting  nbr table value:");
            e.printStackTrace();
        }
        fireTableCellUpdated(row, col);
    }
}
