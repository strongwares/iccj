package org.iotacontrolcenter.ui.dialog;

import org.iotacontrolcenter.ui.app.Constants;
import org.iotacontrolcenter.ui.properties.locale.Localizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ConfigureServerDialog extends JDialog {

    private Localizer localizer;
    private String title;

    public JPanel panel;
    public JTextField serverIpTextField;
    public JTextField iccrPortTextField;
    public JPasswordField iccrPwdTextField;
    public JTextField serverNameTextField;
    public JButton save;
    public JButton cancel;

    public ConfigureServerDialog(Localizer localizer, String title) {
        super();
        this.title = title;
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
        setTitle(title);
        setModal(true);
        setLayout(new BorderLayout());

        panel = new JPanel(new SpringLayout());
        JLabel serverIp = new JLabel(localizer.getLocalText("fieldLabelRemoteServerIp"), JLabel.TRAILING);
        panel.add(serverIp);
        serverIpTextField = new JTextField(10);
        serverIpTextField.setToolTipText(localizer.getLocalText("fieldLabelRemoteServerIpTooltip"));
        serverIp.setLabelFor(serverIpTextField);
        panel.add(serverIpTextField);

        JLabel iccrPort = new JLabel(localizer.getLocalText("fieldLabelIccrPort"), JLabel.TRAILING);
        panel.add(iccrPort);
        iccrPortTextField = new JTextField(10);
        serverIpTextField.setToolTipText(localizer.getLocalText("fieldLabelIccrPortTooltip"));
        iccrPort.setLabelFor(iccrPortTextField);
        panel.add(iccrPortTextField);

        JLabel iccrPassword = new JLabel(localizer.getLocalText("fieldLabelIccrPassword"), JLabel.TRAILING);
        panel.add(iccrPassword);
        iccrPwdTextField = new JPasswordField(10);
        serverIpTextField.setToolTipText(localizer.getLocalText("fieldLabelIccrPasswordTooltip"));
        iccrPassword.setLabelFor(iccrPwdTextField);
        panel.add(iccrPwdTextField);

        JLabel serverName = new JLabel(localizer.getLocalText("fieldLabelServerName"), JLabel.TRAILING);
        panel.add(serverName);
        serverNameTextField = new JTextField(10);
        serverNameTextField.setToolTipText(localizer.getLocalText("fieldLabelServerNameTooltip"));
        serverName.setLabelFor(serverNameTextField);
        panel.add(serverNameTextField);

        SpringUtilities.makeCompactGrid(panel,
                4, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        //setContentPane(panel);
        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));

        cancel = new JButton(localizer.getLocalText("buttonLabelCancel"));
        cancel.setActionCommand(Constants.DIALOG_CONFIG_SERVER_CANCEL);
        buttonPanel.add(cancel);

        buttonPanel.add(Box.createHorizontalGlue());

        save = new JButton(localizer.getLocalText("buttonLabelSave"));
        save.setActionCommand(Constants.DIALOG_CONFIG_SERVER_SAVE);
        buttonPanel.add(save);

        add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        //setUndecorated(true);
        //getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);

        pack();
    }
}
