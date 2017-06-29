package org.iotacontrolcenter.ui.menu;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.iotacontrolcenter.ui.app.Constants;
import org.iotacontrolcenter.ui.controller.MainController;
import org.iotacontrolcenter.ui.properties.locale.Localizer;

public class MainMenu extends JMenuBar {

    private static final long serialVersionUID = -4490811224635367854L;
    private Localizer localizer;
    private MainController mainController;
    private JMenu openAddServer;
    private JButton settings;

    public MainMenu(MainController mainController) {
        super();
        this.mainController = mainController;
        init();
    }

    private void init() {
        localizer = Localizer.getInstance();

        settings = new JButton(localizer.getLocalText("mainMenuSettingsLabel"));
        //settings.setMnemonic(KeyEvent.VK_I);
        settings.setBorder(BorderFactory.createEmptyBorder());
        settings.setContentAreaFilled(false);
        settings.setBorderPainted(false);
        settings.setFocusPainted(false);
        settings.setOpaque(false);
        settings.setActionCommand(Constants.MM_ICC_SETTINGS_ACTION);
        settings.addActionListener(mainController);
        add(settings);

        JButton sep = new JButton("|");
        sep.setMinimumSize(new Dimension(30, 40));
        sep.setMaximumSize(new Dimension(30, 40));
        sep.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        sep.setContentAreaFilled(false);
        sep.setBorderPainted(false);
        sep.setFocusPainted(false);
        add(sep);

        openAddServer = new JMenu(localizer.getLocalText("mainMenuOpenOrAddServerLabel"));
        //openAddServer.setMnemonic(KeyEvent.VK_S);
        openAddServer.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
        //openAddServer.setBorder(BorderFactory.createEmptyBorder());
        //openAddServer.setContentAreaFilled(false);
        //openAddServer.setActionCommand(Constants.OPEN_ADD_SERVER_ACTION);
        //openAddServer.addActionListener(mainController);

        JMenuItem addServer = new JMenuItem(localizer.getLocalText("mainMenuAddServerLabel"));
        //addServer.setMnemonic(KeyEvent.VK_A);
        addServer.setActionCommand(Constants.MM_ADD_SERVER_ACTION);
        addServer.addActionListener(mainController);
        openAddServer.add(addServer);

        JMenuItem openServer = new JMenuItem(localizer.getLocalText("mainMenuOpenServerLabel"));
        //openServer.setMnemonic(KeyEvent.VK_M);
        openServer.setActionCommand(Constants.MM_OPEN_SERVER_ACTION);
        openServer.addActionListener(mainController);
        openAddServer.add(openServer);

        add(openAddServer);

        add(Box.createHorizontalGlue());
    }
}
