package org.iotacontrolcenter.ui.dialog;


import org.iotacontrolcenter.ui.app.Constants;
import org.iotacontrolcenter.ui.properties.locale.Localizer;
import org.iotacontrolcenter.ui.util.UiUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class IotaLogDialog extends JDialog {

    private ActionListener ctlr;
    private String[] dirChoices;
    public JComboBox<String> dirChooser;
    //public JCheckBox head;
    public JButton headAdd;
    public String headChoice;
    private Localizer localizer;
    public JTextArea logText;
    public Long refreshLastFilePosition;
    public Long refreshLastFileSize;
    //public JCheckBox tail;
    public String tailChoice;
    public JButton tailPlay;
    public JButton tailPause;
    private String title;

    public IotaLogDialog(Localizer localizer, String title, ActionListener ctlr) {
        super();
        this.title = title;
        this.localizer = localizer;
        this.ctlr = ctlr;
        init();
    }

    private void init() {
        setTitle(title);
        setModal(false);
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

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        //head = new JButton(localizer.getLocalText("buttonLabelIotaLogHead"));
        //head = new JToggleButton(localizer.getLocalText("dialogButtonLabelIotaLogHead"), null, false);
        //head.setToolTipText(localizer.getLocalText("dialogButtonLabelIotaLogHeadTooltip"));

        /*
        JLabel headLabel = new JLabel(localizer.getLocalText("dialogButtonLabelIotaLogHead") + ":");
        buttonPanel.add(headLabel);
        head = new JCheckBox(null, null, true);
        head.setToolTipText(localizer.getLocalText("dialogButtonLabelIotaLogHeadTooltip"));
        head.setAlignmentX(Component.CENTER_ALIGNMENT);
        head.setActionCommand(Constants.DIALOG_IOTA_LOG_HEAD);
        head.addActionListener(ctlr);
        buttonPanel.add(head);
        */
        headAdd = new JButton(UiUtil.loadIcon(Constants.IMAGE_ICON_FILENAME_GREEN_ADD));
        headAdd.setToolTipText(localizer.getLocalText("dialogIotaLogHeadMoreTooltip"));
        headAdd.setActionCommand(Constants.DIALOG_IOTA_LOG_HEAD_MORE);
        headAdd.addActionListener(ctlr);
        headAdd.setEnabled(false);
        buttonPanel.add(headAdd);

        buttonPanel.add(Box.createHorizontalGlue());

        tailChoice = localizer.getLocalText("dialogIotaLogTail");
        headChoice = localizer.getLocalText("dialogIotaLogHead");
        dirChoices = new String[] {  tailChoice, headChoice };

        dirChooser = new JComboBox<>(dirChoices);
        dirChooser.setToolTipText(localizer.getLocalText("dialogIotaLogDirectionTooltip"));
        dirChooser.setAlignmentX(Component.CENTER_ALIGNMENT);
        dirChooser.setActionCommand(Constants.DIALOG_IOTA_LOG_DIRECTION_CHOOSER);
        dirChooser.setSelectedIndex(0);
        dirChooser.addActionListener(ctlr);
        buttonPanel.add(dirChooser);

        buttonPanel.add(Box.createHorizontalGlue());

        //tail = new JButton(localizer.getLocalText("buttonLabelIotaLogTail"));
        //tail = new JButton(localizer.getLocalText("buttonLabelIotaLogTail"), null, true);
        //tail = new JToggleButton(localizer.getLocalText("dialogButtonLabelIotaLogTail"), null, true);

        /*
        JLabel tailLabel = new JLabel(localizer.getLocalText("dialogButtonLabelIotaLogTail") + ":");
        buttonPanel.add(tailLabel);
        tail = new JCheckBox(null, null, true);
        tail.setToolTipText(localizer.getLocalText("dialogButtonLabelIotaLogTailTooltip"));
        tail.setActionCommand(Constants.DIALOG_IOTA_LOG_TAIL);
        tail.addActionListener(ctlr);
        buttonPanel.add(tail);
        */

        tailPlay = new JButton(UiUtil.loadIcon(Constants.IMAGE_ICON_FILENAME_PLAY_PRESSED));
        tailPlay.setToolTipText(localizer.getLocalText("dialogIotaLogTailPlayTooltip"));
        tailPlay.setActionCommand(Constants.DIALOG_IOTA_LOG_TAIL_PLAY);
        tailPlay.addActionListener(ctlr);
        buttonPanel.add(tailPlay);

        tailPause = new JButton(UiUtil.loadIcon(Constants.IMAGE_ICON_FILENAME_PAUSE_UNPRESSED));
        tailPause.setToolTipText(localizer.getLocalText("dialogIotaLogTailPauseTooltip"));
        tailPause.setActionCommand(Constants.DIALOG_IOTA_LOG_TAIL_PAUSE);
        tailPause.addActionListener(ctlr);
        buttonPanel.add(tailPause);

        add(buttonPanel,  BorderLayout.SOUTH);

        pack();
    }

}
