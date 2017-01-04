package org.iotacontrolcenter.ui.dialog;

import org.iotacontrolcenter.ui.app.Constants;
import org.iotacontrolcenter.ui.app.Main;
import org.iotacontrolcenter.ui.properties.locale.Localizer;
import org.iotacontrolcenter.ui.properties.source.PropertySource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Properties;

public class ConfigureServerDialog extends JDialog {

    public JButton cancel;
    private ActionListener ctlr;
    public boolean isAdd;
    private Localizer localizer;
    private String title;
    public JPanel panel;
    public JTextField serverIpTextField;
    public JTextField iccrPortTextField;
    public JPasswordField iccrPwdTextField;
    public JTextField serverNameTextField;
    public JButton save;
    public Properties serverProps;
    public JLabel walletCmdLabel;
    public JTextField walletCmdTextField;

    public ConfigureServerDialog(Localizer localizer, String title, ActionListener ctlr, Properties serverProps, boolean isAdd) {
        super();
        this.title = title;
        this.localizer = localizer;
        this.ctlr = ctlr;
        this.serverProps = serverProps;
        this.isAdd = isAdd;
        init();
    }

    private void init() {
        setTitle(title);
        setIconImages(Main.icons);
        setModal(true);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);

        panel = new JPanel(new SpringLayout());

        JLabel serverIp = new JLabel(localizer.getLocalText("fieldLabelRemoteServerIp") + ":", JLabel.TRAILING);
        panel.add(serverIp);

        serverIpTextField = new JTextField(10);
        serverIpTextField.setName(localizer.getLocalText("fieldLabelRemoteServerIp"));
        serverIpTextField.setToolTipText(localizer.getLocalText("fieldLabelRemoteServerIpTooltip"));
        serverIp.setLabelFor(serverIpTextField);
        panel.add(serverIpTextField);

        JLabel iccrPort = new JLabel(localizer.getLocalText("fieldLabelIccrPort") + ":", JLabel.TRAILING);
        panel.add(iccrPort);
        iccrPortTextField = new JTextField(10);
        iccrPortTextField.setName(localizer.getLocalText("fieldLabelIccrPort"));
        iccrPortTextField.setToolTipText(localizer.getLocalText("fieldLabelIccrPortTooltip"));
        iccrPort.setLabelFor(iccrPortTextField);
        panel.add(iccrPortTextField);

        JLabel iccrPassword = new JLabel(localizer.getLocalText("fieldLabelIccrPassword") + ":", JLabel.TRAILING);
        panel.add(iccrPassword);
        iccrPwdTextField = new JPasswordField(10);
        iccrPwdTextField.setName(localizer.getLocalText("fieldLabelIccrPassword"));
        iccrPwdTextField.setToolTipText(localizer.getLocalText("fieldLabelIccrPasswordTooltip"));
        iccrPassword.setLabelFor(iccrPwdTextField);
        panel.add(iccrPwdTextField);

        JLabel serverName = new JLabel(localizer.getLocalText("fieldLabelServerName") + ":", JLabel.TRAILING);
        panel.add(serverName);
        serverNameTextField = new JTextField(10);
        serverNameTextField.setName(localizer.getLocalText("fieldLabelServerName"));
        serverNameTextField.setToolTipText(localizer.getLocalText("fieldLabelServerNameTooltip"));
        serverName.setLabelFor(serverNameTextField);
        panel.add(serverNameTextField);

        /*
        walletCmdLabel = new JLabel(localizer.getLocalText("fieldLabelServerWalletCommand") + ":", JLabel.TRAILING);
        panel.add(walletCmdLabel);
        walletCmdTextField = new JTextField(10);
        walletCmdTextField.setName(localizer.getLocalText("fieldLabelServerWalletCommand"));
        walletCmdTextField.setToolTipText(localizer.getLocalText("fieldLabelServerWalletCommandTooltip"));
        walletCmdLabel.setLabelFor(walletCmdTextField);
        panel.add(walletCmdTextField);
        */

        SpringUtilities.makeCompactGrid(panel,
                4, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        //setContentPane(panel);
        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        cancel = new JButton(localizer.getLocalText("buttonLabelCancel"));
        cancel.setActionCommand(Constants.DIALOG_CONFIG_SERVER_CANCEL);
        cancel.addActionListener(ctlr);
        buttonPanel.add(cancel);

        buttonPanel.add(Box.createHorizontalGlue());

        save = new JButton(localizer.getLocalText("buttonLabelSave"));
        save.setActionCommand(isAdd ? Constants.DIALOG_CONFIG_ADD_SERVER_SAVE : Constants.DIALOG_CONFIG_EDIT_SERVER_SAVE);
        save.addActionListener(ctlr);
        buttonPanel.add(save);

        getRootPane().setDefaultButton(save);

        add(buttonPanel, BorderLayout.SOUTH);

        if(!isAdd && serverProps != null) {
            insertValues();
        }
        /*
        else {
            walletCmdTextField.setVisible(false);
            walletCmdLabel.setVisible(false);
        }
        */

        pack();
    }

    private void insertValues() {
        serverIpTextField.setText(serverProps.getProperty(PropertySource.SERVER_IP_PROP));
        iccrPortTextField.setText(serverProps.getProperty(PropertySource.SERVER_ICCR_PORT_NUM_PROP));
        iccrPwdTextField.setText(serverProps.getProperty(PropertySource.SERVER_ICCR_API_KEY_PROP));
        serverNameTextField.setText(serverProps.getProperty(PropertySource.SERVER_NAME_PROP));
        //walletCmdTextField.setText(serverProps.getProperty(PropertySource.SERVER_WALLET_CMD_PROP));

        /*
        if(UiUtil.isLocalhostIp(serverProps.getProperty(PropertySource.SERVER_IP_PROP))) {
            walletCmdTextField.setVisible(true);
            walletCmdLabel.setVisible(true);
        }
        else {
            walletCmdTextField.setVisible(false);
            walletCmdLabel.setVisible(false);
        }
        */
    }
}
