package org.iotacontrolcenter.ui.dialog;


import org.iotacontrolcenter.ui.app.Constants;
import org.iotacontrolcenter.ui.properties.locale.Localizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class IccSettingsDialog extends JDialog {

    private Localizer localizer;

    public JButton cancel;
    public JTextField nbrRefreshTimeTextField;
    public JTextField nodeInfoRefreshTimeTextField;
    public JTextField iotaDownloadLinkTextField;
    public JPanel panel;
    public JButton save;


    public IccSettingsDialog(Localizer localizer) {
        super();
        this.localizer = localizer;
        init();
    }

    public void addCtlr(ActionListener actionListener) {
        if (save != null) {
            save.addActionListener(actionListener);
        }
        if (cancel != null) {
            cancel.addActionListener(actionListener);
        }
    }

    private void init() {
        setTitle(localizer.getLocalText("dialogTitleIccSettings"));
        setModal(true);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);

        panel = new JPanel(new SpringLayout());

        JLabel nbrRefreshTime = new JLabel(localizer.getLocalText("fieldLabelNeighborRefreshTime") + ":", JLabel.TRAILING);
        panel.add(nbrRefreshTime);
        nbrRefreshTimeTextField = new JTextField(5);
        nbrRefreshTimeTextField.setToolTipText(localizer.getLocalText("fieldLabelNeighborRefreshTimeTooltip"));
        nbrRefreshTime.setLabelFor(nbrRefreshTimeTextField);
        panel.add(nbrRefreshTimeTextField);

        JLabel nodeInfoRefreshTime = new JLabel(localizer.getLocalText("fieldLabelNodeInfoRefreshTime") + ":", JLabel.TRAILING);
        panel.add(nodeInfoRefreshTime);
        nodeInfoRefreshTimeTextField = new JTextField(10);
        nodeInfoRefreshTimeTextField.setToolTipText(localizer.getLocalText("fieldLabelNodeInfoRefreshTimeTooltip"));
        nodeInfoRefreshTime.setLabelFor(nodeInfoRefreshTimeTextField);
        panel.add(nodeInfoRefreshTimeTextField);

        JLabel iotaDownloadLink = new JLabel(localizer.getLocalText("fieldLabelIotaDownloadLink") + ":", JLabel.TRAILING);
        panel.add(iotaDownloadLink);
        iotaDownloadLinkTextField = new JTextField(5);
        iotaDownloadLinkTextField.setToolTipText(localizer.getLocalText("fieldLabelIotaDownloadLinkTooltip"));
        iotaDownloadLink.setLabelFor(iotaDownloadLinkTextField);
        panel.add(iotaDownloadLinkTextField);

        SpringUtilities.makeCompactGrid(panel,
                3, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        //setContentPane(panel);
        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        cancel = new JButton(localizer.getLocalText("buttonLabelCancel"));
        cancel.setActionCommand(Constants.DIALOG_ICC_SETTINGS_CANCEL);
        buttonPanel.add(cancel);

        buttonPanel.add(Box.createHorizontalGlue());

        save = new JButton(localizer.getLocalText("buttonLabelSave"));
        save.setActionCommand(Constants.DIALOG_ICC_SETTINGS_SAVE);
        buttonPanel.add(save);

        add(buttonPanel, BorderLayout.SOUTH);

        pack();
    }
}
