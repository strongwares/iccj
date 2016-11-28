package org.iotacontrolcenter.ui.menu;

import org.iotacontrolcenter.ui.controller.MainController;
import org.iotacontrolcenter.ui.properties.locale.Localizer;

import javax.swing.*;

public class MainMenu extends JMenuBar {

    private Localizer localizer;
    private MainController mainController;
    private JMenuItem openAddServer;
    private JMenuItem settings;

    public MainMenu(MainController mainController) {
        super();
        this.mainController = mainController;
        init();
    }

    private void init() {
        localizer = Localizer.getInstance();

        settings = new JMenuItem("ICC " + localizer.getLocalText("mainMenuSettingsLabel"));
        settings.setActionCommand("icc-settings");
        settings.addActionListener(mainController);
        add(settings);

        openAddServer = new JMenuItem(localizer.getLocalText("mainMenuOpenOrAddServerLabel") + "...");
        openAddServer.setActionCommand("openoraddserver");
        openAddServer.addActionListener(mainController);
        add(openAddServer);
    }
}
