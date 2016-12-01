package org.iotacontrolcenter.ui.dialog;

import org.iotacontrolcenter.ui.app.Constants;
import org.iotacontrolcenter.ui.properties.locale.Localizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ServerSettingsDialog extends JDialog {

    private Localizer localizer;
    public JPanel panel;
    public JTextField iotaFolderTextField;
    public JTextField iccrPortTextField;
    public JTextField nbrRefreshTextField;
    public JTextField iotaPortTextField;
    public JTextField iotaStartTextField;
    public JButton save;
    public JButton cancel;

    public ServerSettingsDialog(Localizer localizer) {
        super();
        this.localizer = localizer;
        init();
    }

    public void addCtlr(ActionListener actionListener) {
        if(save != null) {
            save.addActionListener(actionListener);
        }
        if(cancel != null) {
            cancel.addActionListener(actionListener);
        }
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
        panel.add(iccrPortTextField);

        JLabel iotaFolderLocation = new JLabel(localizer.getLocalText("fieldLabelIotaFolderLocation") + ":", JLabel.TRAILING);
        panel.add(iotaFolderLocation);
        iotaFolderTextField = new JTextField(20);
        iotaFolderTextField.setToolTipText(localizer.getLocalText("fieldLabelIotaFolderLocationTooltip"));
        iotaFolderLocation.setLabelFor(iotaFolderTextField);
        panel.add(iotaFolderTextField);

        JLabel iotaStartCmd = new JLabel(localizer.getLocalText("fieldLabelIotaStartCmd") + ":", JLabel.TRAILING);
        panel.add(iotaStartCmd);
        iotaStartTextField = new JTextField(20);
        iotaStartTextField.setToolTipText(localizer.getLocalText("fieldLabelIotaStartCmdTooltip"));
        iotaStartCmd.setLabelFor(iotaStartTextField);
        panel.add(iotaStartTextField);

        JLabel iotaPort = new JLabel(localizer.getLocalText("fieldLabelIotaPort") + ":", JLabel.TRAILING);
        panel.add(iotaPort);
        iotaPortTextField = new JTextField(20);
        iotaPortTextField.setToolTipText(localizer.getLocalText("fieldLabelIotaPortTooltip"));
        iotaPort.setLabelFor(iotaPortTextField);
        panel.add(iotaPortTextField);

        JLabel nbrRefreshTime = new JLabel(localizer.getLocalText("fieldLabelNbrRefreshTime") + ":", JLabel.TRAILING);
        panel.add(nbrRefreshTime);
        nbrRefreshTextField = new JTextField(20);
        nbrRefreshTextField.setToolTipText(localizer.getLocalText("fieldLabelNbrRefreshTimeTooltip"));
        nbrRefreshTime.setLabelFor(nbrRefreshTextField);
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
        cancel.setActionCommand(Constants.DIALOG_CONFIG_SERVER_CANCEL);
        buttonPanel.add(cancel);

        buttonPanel.add(Box.createHorizontalGlue());

        save = new JButton(localizer.getLocalText("buttonLabelSave"));
        save.setActionCommand(Constants.DIALOG_CONFIG_SERVER_SAVE);
        buttonPanel.add(save);

        add(buttonPanel, BorderLayout.SOUTH);

        pack();
    }
}
