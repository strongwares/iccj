package org.iotacontrolcenter.ui.dialog;


import org.iotacontrolcenter.ui.properties.locale.Localizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class IotaLogDialog extends JDialog {

    //public JButton clear;
    private ActionListener ctlr;
    private Localizer localizer;
    private String title;
    public JTextArea logText;

    public IotaLogDialog(Localizer localizer, String title, ActionListener ctlr) {
        super();
        this.title = title;
        this.localizer = localizer;
        this.ctlr = ctlr;
        init();
    }

    private void init() {
        setTitle(title);
        setModal(true);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 500));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        logText = new JTextArea(30, 50);
        logText.setBackground(Color.black);
        logText.setForeground(Color.yellow);
        logText.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(logText);
        scrollPane.getVerticalScrollBar().setUnitIncrement(50);

        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        /*
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        clear = new JButton(localizer.getLocalText("buttonLabelClearIccrEventLog"));
        clear.setToolTipText(localizer.getLocalText("buttonLabelClearIccrEventLogTooltip"));
        clear.setActionCommand(Constants.SERVER_ACTION_CLEAR_ICCR_EVENTLOG);
        clear.addActionListener(ctlr);
        buttonPanel.add(clear);

        buttonPanel.add(Box.createHorizontalGlue());

        add(buttonPanel,  BorderLayout.SOUTH);
        */

        pack();

    }

}
