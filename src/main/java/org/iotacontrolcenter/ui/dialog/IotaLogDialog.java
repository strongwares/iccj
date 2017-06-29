package org.iotacontrolcenter.ui.dialog;


import org.iotacontrolcenter.dto.LogLinesResponse;
import org.iotacontrolcenter.ui.app.Constants;
import org.iotacontrolcenter.ui.app.Main;
import org.iotacontrolcenter.ui.controller.ServerController;
import org.iotacontrolcenter.ui.properties.locale.Localizer;
import org.iotacontrolcenter.ui.util.UiUtil;

import javax.swing.*;
import java.awt.*;

public class IotaLogDialog extends JDialog {

    private static final long serialVersionUID = -7678882820998863056L;
    private ServerController ctlr;
    private String[] dirChoices;
    private String[]  dirChoiceTooltips;
    public JComboBox<String> dirChooser;
    //public JCheckBox head;
    public JButton headAdd;
    public String headChoice;
    private Localizer localizer;
    public JTextArea logText;
    public long numLines = 0;
    public Long refreshLastFilePosition;
    public Long refreshLastFileSize;
    //public JCheckBox tail;
    public String tailChoice;
    public JButton tailPlay;
    public JButton tailPause;
    private String title;

    public IotaLogDialog(Localizer localizer, String title, ServerController ctlr) {
        super();
        this.title = title;
        this.localizer = localizer;
        this.ctlr = ctlr;
        init();
    }

    public void addNewLines(LogLinesResponse resp) {
        long prevNumLines = numLines;
        long numNewLines = resp.getLines().size();
        boolean doRemove = ((prevNumLines + numNewLines) > Constants.IOTA_LOG_QP_NUMLINES_DEFAULT * 2);

        if(doRemove) {
            long numLinesToRemove = ((prevNumLines + numNewLines) - (Constants.IOTA_LOG_QP_NUMLINES_DEFAULT * 2));
            try {
                logText.replaceRange("", 0, logText.getLineEndOffset((int)numLinesToRemove));
                numLines  -= numLinesToRemove;
            }
            catch(Exception e) {
                System.out.println("iota log line remove exception: " + e);
            }
        }

        for (String s : resp.getLines()) {
            logText.append(s + "\n");
            numLines++;
        }
        logText.setCaretPosition(logText.getDocument().getLength());
        refreshLastFilePosition =  resp.getLastFilePosition();
        refreshLastFileSize =  resp.getLastFileSize();
    }

    private void init() {
        setIconImages(Main.icons);
        setTitle(ctlr.name + " " + title);
        setModal(false);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 500));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        logText = new JTextArea(30, 50);
        logText.setBackground(Color.black);
        logText.setForeground(Color.yellow);
        logText.setEditable(false);

        //DefaultCaret caret = (DefaultCaret)logText.getCaret();
        //caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

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
        dirChoiceTooltips = new String[]{
                localizer.getLocalText("dialogIotaLogTailTooltip"),
                localizer.getLocalText("dialogIotaLogHeadTooltip")
        };

        dirChooser = new JComboBox<>(dirChoices);
        dirChooser.setRenderer(new DefaultListCellRenderer() {
			       private static final long serialVersionUID = -8993843708407522418L;

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object val, int idx, boolean isSel, boolean cellHasFocus) {
                JComponent comp = (JComponent)super.getListCellRendererComponent(list, val, idx, isSel, cellHasFocus);
                if (-1 < idx && null != val) {
                    list.setToolTipText(dirChoiceTooltips[idx]);
                }
                return comp;
            }
        });
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
