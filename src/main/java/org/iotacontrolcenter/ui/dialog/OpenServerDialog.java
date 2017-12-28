package org.iotacontrolcenter.ui.dialog;


import org.iotacontrolcenter.ui.app.Constants;
import org.iotacontrolcenter.ui.app.Main;
import org.iotacontrolcenter.ui.properties.locale.Localizer;
import org.iotacontrolcenter.ui.properties.source.PropertySource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class OpenServerDialog extends JDialog  {

    private static final long serialVersionUID = -6072121209754382479L;
    private Localizer localizer;
    private String title;

    public JButton addServer;
    public JPanel buttonPanel;
    public JButton cancel;
    private ActionListener ctlr;
    public JButton editServer;
    public JButton openServer;
    public JButton closeServer;
    private PropertySource propertySource = PropertySource.getInstance();
    public JButton remove;
    public JPanel serverActionPanel;
    public JList<String> serverList;
    public DefaultListModel<String> serverListModel;
    public JScrollPane serverPanel;

    public OpenServerDialog(Localizer localizer, String title, ActionListener ctlr) {
        super();
        this.title = title;
        this.localizer = localizer;
        this.ctlr = ctlr;
        init();
    }

    private void init() {
        setIconImages(Main.icons);
        setTitle(title);
        setModal(true);
        setLayout(new BorderLayout());
        //setPreferredSize(new Dimension(150, 250));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);

        serverListModel = new DefaultListModel<>();
        serverList = new JList<>(serverListModel);

        int idToSelect = -1;
        for(String id : propertySource.getServerIds()) {
            System.out.println("adding server id " + id);
            if(idToSelect < 0) {
                idToSelect++;
            }
            serverListModel.addElement(propertySource.getServerName(id));
        }
        if(idToSelect >= 0) {
            serverList.setSelectedIndex(idToSelect);
        }

        serverPanel = new JScrollPane(serverList);
        serverPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        serverPanel.setPreferredSize(new Dimension(125, 200));
        serverPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        serverPanel.setBackground(Color.white);
        serverPanel.getViewport().setBackground(Color.white);
        serverPanel.getVerticalScrollBar().setUnitIncrement(50);

        add(serverPanel, BorderLayout.WEST);

        serverActionPanel = new JPanel();
        serverActionPanel.setLayout(new SpringLayout());
        serverActionPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        openServer = new JButton(localizer.getLocalText("buttonLabelOpen"));
        openServer.setAlignmentX(Component.CENTER_ALIGNMENT);
        openServer.setActionCommand(Constants.DIALOG_OPEN_SERVER_OPEN);
        openServer.addActionListener(ctlr);
        serverActionPanel.add(openServer);

        closeServer = new JButton(localizer.getLocalText("buttonLabelClose"));
        closeServer.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeServer.setActionCommand(Constants.DIALOG_OPEN_SERVER_CLOSE);
        closeServer.addActionListener(ctlr);
        serverActionPanel.add(closeServer);

        if(idToSelect >= 0) {
            getRootPane().setDefaultButton(openServer);
        }

        editServer = new JButton(localizer.getLocalText("buttonLabelEdit"));
        editServer.setActionCommand(Constants.DIALOG_OPEN_SERVER_EDIT);
        editServer.setAlignmentX(Component.CENTER_ALIGNMENT);
        editServer.addActionListener(ctlr);
        serverActionPanel.add(editServer);

        addServer = new JButton(localizer.getLocalText("buttonLabelAddServer"));
        addServer.setActionCommand(Constants.DIALOG_OPEN_SERVER_ADD_SERVER);
        addServer.setAlignmentX(Component.CENTER_ALIGNMENT);
        addServer.addActionListener(ctlr);
        serverActionPanel.add(addServer);

        SpringUtilities.makeCompactGrid(serverActionPanel,
                4, 1, //rows, cols
                8, 8,        //initX, initY
                6, 6);       //xPad, yPad


        add(serverActionPanel, BorderLayout.EAST);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        remove = new JButton(localizer.getLocalText("buttonLabelRemoveSelected"));
        remove.setActionCommand(Constants.DIALOG_OPEN_SERVER_REMOVE);
        remove.addActionListener(ctlr);
        buttonPanel.add(remove);

        buttonPanel.add(Box.createHorizontalGlue());

        cancel = new JButton(localizer.getLocalText("buttonLabelCancel"));
        cancel.setActionCommand(Constants.DIALOG_OPEN_SERVER_CANCEL);
        cancel.addActionListener(ctlr);
        buttonPanel.add(cancel);

        add(buttonPanel, BorderLayout.SOUTH);

        pack();
    }
}
