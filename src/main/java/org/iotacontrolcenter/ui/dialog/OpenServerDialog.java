package org.iotacontrolcenter.ui.dialog;


import org.iotacontrolcenter.ui.app.Constants;
import org.iotacontrolcenter.ui.properties.locale.Localizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class OpenServerDialog extends JDialog  {

    private Localizer localizer;
    private String title;

    public JButton addServer;
    public JPanel buttonPanel;
    public JButton cancel;
    public JButton editServer;
    public JButton openServer;
    public JButton remove;
    public JPanel serverActionPanel;
    public JList<String> serverList;
    public JScrollPane serverPanel;

    public OpenServerDialog(Localizer localizer, String title) {
        super();
        this.title = title;
        this.localizer = localizer;
        init();
    }

    public void addCtlr(ActionListener actionListener) {
        if(remove != null) {
            remove.addActionListener(actionListener);
        }
        if(cancel != null) {
            cancel.addActionListener(actionListener);
        }
        if(addServer != null) {
            addServer.addActionListener(actionListener);
        }
        if(editServer != null) {
            editServer.addActionListener(actionListener);
        }
        if(openServer != null) {
            openServer.addActionListener(actionListener);
        }
    }

    private void init() {
        setTitle(title);
        setModal(true);
        setLayout(new BorderLayout());
        //setPreferredSize(new Dimension(150, 250));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);

        serverList = new JList<>();

        serverPanel = new JScrollPane(serverList);
        serverPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        serverPanel.setPreferredSize(new Dimension(125, 200));
        serverPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        serverPanel.setBackground(Color.white);
        serverPanel.getViewport().setBackground(Color.white);
        serverPanel.getVerticalScrollBar().setUnitIncrement(50);

        add(serverPanel, BorderLayout.WEST);

        serverActionPanel = new JPanel();
        serverActionPanel.setLayout(new BoxLayout(serverActionPanel, BoxLayout.PAGE_AXIS));
        serverActionPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        openServer = new JButton(localizer.getLocalText("buttonLabelOpen"));
        openServer.setAlignmentX(Component.CENTER_ALIGNMENT);
        openServer.setActionCommand(Constants.DIALOG_OPEN_SERVER_OPEN);
        serverActionPanel.add(openServer);

        editServer = new JButton(localizer.getLocalText("buttonLabelEdit"));
        editServer.setActionCommand(Constants.DIALOG_OPEN_SERVER_EDIT);
        editServer.setAlignmentX(Component.CENTER_ALIGNMENT);
        serverActionPanel.add(editServer);

        addServer = new JButton(localizer.getLocalText("buttonLabelAddServer"));
        addServer.setActionCommand(Constants.DIALOG_OPEN_SERVER_ADD_SERVER);
        addServer.setAlignmentX(Component.CENTER_ALIGNMENT);
        serverActionPanel.add(addServer);

        add(serverActionPanel, BorderLayout.EAST);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        remove = new JButton(localizer.getLocalText("buttonLabelRemoveSelected"));
        remove.setActionCommand(Constants.DIALOG_OPEN_SERVER_REMOVE);
        buttonPanel.add(remove);

        buttonPanel.add(Box.createHorizontalGlue());

        cancel = new JButton(localizer.getLocalText("buttonLabelCancel"));
        cancel.setActionCommand(Constants.DIALOG_OPEN_SERVER_CANCEL);
        buttonPanel.add(cancel);

        add(buttonPanel, BorderLayout.SOUTH);

        pack();
    }
}
