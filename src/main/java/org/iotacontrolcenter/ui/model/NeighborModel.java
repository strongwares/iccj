package org.iotacontrolcenter.ui.model;


import org.iotacontrolcenter.ui.properties.locale.Localizer;

import javax.swing.table.AbstractTableModel;

public class NeighborModel extends AbstractTableModel {

    private String[] columnNames;
    private Object[][] data;
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
        data = null; //new Object[][]{{}};
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data == null ? 0 : data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        if(data == null || data.length <= row ||
                data[row] == null || data[row].length <= col) {
            return null;
        }
        return data[row][col];
    }

    public Class getColumnClass(int c) {
        Object o = getValueAt(0, c);
        if(o == null) {
            return null;
        }
        return o.getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {

            System.out.println("Setting value at " + row + "," + col
                    + " to " + value
                    + " (an instance of "
                    + value.getClass() + ")");

        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}
