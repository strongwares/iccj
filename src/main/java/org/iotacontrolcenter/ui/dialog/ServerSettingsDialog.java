package org.iotacontrolcenter.ui.dialog;

import org.iotacontrolcenter.ui.app.Constants;
import org.iotacontrolcenter.ui.properties.locale.Localizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Properties;

public class ServerSettingsDialog extends JDialog {

    public JButton cancel;
    private ActionListener ctlr;
    private Localizer localizer;
    public Properties iccrProps;
    public JTextField iccrPortTextField;
    public JTextField iotaPortTextField;
    public JTextField iotaStartTextField;
    public JTextField iotaFolderTextField;
    public JTextField nbrRefreshTextField;
    public JPanel panel;
    public java.util.List<String> propList;
    public JButton save;

    public ServerSettingsDialog(Localizer localizer, ActionListener ctlr, Properties iccrProps) {
        super();
        this.localizer = localizer;
        this.ctlr = ctlr;
        this.iccrProps = iccrProps;
        init();
    }

    private void init() {
        setTitle(localizer.getLocalText("dialogTitleSettings"));
        setModal(true);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);

        panel = new JPanel(new SpringLayout());

        JLabel iccrPort = new JLabel(localizer.getLocalText("fieldLabelIccrPort") + ":", JLabel.TRAILING);
        panel.add(iccrPort);
        iccrPortTextField = new JTextField(20);
        iccrPortTextField.setToolTipText(localizer.getLocalText("fieldLabelIccrPortTooltip"));
        iccrPort.setLabelFor(iccrPortTextField);
        iccrPortTextField.setName(localizer.getLocalText("fieldLabelIccrPort"));
        panel.add(iccrPortTextField);

        JLabel iotaFolderLocation = new JLabel(localizer.getLocalText("fieldLabelIotaFolderLocation") + ":", JLabel.TRAILING);
        panel.add(iotaFolderLocation);
        iotaFolderTextField = new JTextField(20);
        iotaFolderTextField.setToolTipText(localizer.getLocalText("fieldLabelIotaFolderLocationTooltip"));
        iotaFolderLocation.setLabelFor(iotaFolderTextField);
        iotaFolderTextField.setName(localizer.getLocalText("fieldLabelIotaFolderLocation"));
        panel.add(iotaFolderTextField);

        JLabel iotaStartCmd = new JLabel(localizer.getLocalText("fieldLabelIotaStartCmd") + ":", JLabel.TRAILING);
        panel.add(iotaStartCmd);
        iotaStartTextField = new JTextField(20);
        iotaStartTextField.setToolTipText(localizer.getLocalText("fieldLabelIotaStartCmdTooltip"));
        iotaStartCmd.setLabelFor(iotaStartTextField);
        iotaStartTextField.setName(localizer.getLocalText("fieldLabelIotaStartCmd"));
        panel.add(iotaStartTextField);

        JLabel iotaPort = new JLabel(localizer.getLocalText("fieldLabelIotaPort") + ":", JLabel.TRAILING);
        panel.add(iotaPort);
        iotaPortTextField = new JTextField(20);
        iotaPortTextField.setToolTipText(localizer.getLocalText("fieldLabelIotaPortTooltip"));
        iotaPort.setLabelFor(iotaPortTextField);
        iotaPortTextField.setName(localizer.getLocalText("fieldLabelIotaPort"));
        panel.add(iotaPortTextField);

        JLabel nbrRefreshTime = new JLabel(localizer.getLocalText("fieldLabelNbrRefreshTime") + ":", JLabel.TRAILING);
        panel.add(nbrRefreshTime);
        nbrRefreshTextField = new JTextField(20);
        nbrRefreshTextField.setToolTipText(localizer.getLocalText("fieldLabelNbrRefreshTimeTooltip"));
        nbrRefreshTime.setLabelFor(nbrRefreshTextField);
        nbrRefreshTextField.setName(localizer.getLocalText("fieldLabelNbrRefreshTime"));
        panel.add(nbrRefreshTextField);

        SpringUtilities.makeCompactGrid(panel,
                5, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        //setContentPane(panel);
        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        cancel = new JButton(localizer.getLocalText("buttonLabelCancel"));
        cancel.setActionCommand(Constants.DIALOG_SERVER_SETTINGS_CANCEL);
        cancel.addActionListener(ctlr);
        buttonPanel.add(cancel);

        buttonPanel.add(Box.createHorizontalGlue());

        save = new JButton(localizer.getLocalText("buttonLabelSave"));
        save.setActionCommand(Constants.DIALOG_SERVER_SETTINGS_SAVE);
        save.addActionListener(ctlr);
        buttonPanel.add(save);

        getRootPane().setDefaultButton(save);

        add(buttonPanel, BorderLayout.SOUTH);

        if(iccrProps != null) {
            insertValues();
        }

        pack();
    }

    private void insertValues() {
        propList = new ArrayList<>();

        iccrPortTextField.setText(iccrProps.getProperty("iccrPortNumber"));
        propList.add("iccrPortNumber");

        iotaFolderTextField.setText(iccrProps.getProperty("iotaDir"));
        propList.add("iotaDir");

        nbrRefreshTextField.setText(iccrProps.getProperty("iotaNeighborRefreshTime"));
        propList.add("iotaNeighborRefreshTime");

        iotaPortTextField.setText(iccrProps.getProperty("iotaPortNumber"));
        propList.add("iotaPortNumber");

        iotaStartTextField.setText(iccrProps.getProperty("iotaStartCmd"));
        propList.add("iotaStartCmd");

    }
}
