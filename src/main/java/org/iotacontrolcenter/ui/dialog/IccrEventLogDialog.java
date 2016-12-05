package org.iotacontrolcenter.ui.dialog;

import org.iotacontrolcenter.ui.app.Constants;
import org.iotacontrolcenter.ui.properties.locale.Localizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class IccrEventLogDialog extends JDialog {

    public JButton clear;
    private ActionListener ctlr;
    private Localizer localizer;
    private String title;
    public JTextArea eventText;

    public IccrEventLogDialog(Localizer localizer, String title, ActionListener ctlr) {
        super();
        this.title = title;
        this.localizer = localizer;
        this.ctlr = ctlr;
        init();
    }

    private void init() {
        System.out.println("iccr event log dialog init");
        setTitle(title);
        setModal(true);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 500));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        eventText = new JTextArea(30, 50);
        eventText.setBackground(Color.black);
        eventText.setForeground(Color.yellow);
        eventText.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(eventText);
        scrollPane.getVerticalScrollBar().setUnitIncrement(50);

        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        //scrollPane.setPreferredSize(new Dimension(250, 500));
        add(scrollPane, BorderLayout.CENTER);

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

        pack();

    }

}
