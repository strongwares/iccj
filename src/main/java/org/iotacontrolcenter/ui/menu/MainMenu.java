package org.iotacontrolcenter.ui.menu;

import org.iotacontrolcenter.ui.controller.MainController;
import org.iotacontrolcenter.ui.properties.locale.Localizer;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JMenuBar {

    private Localizer localizer;
    private MainController mainController;
    private JButton openAddServer;
    private JButton settings;

    public MainMenu(MainController mainController) {
        super();
        this.mainController = mainController;
        init();
    }

    private void init() {
        localizer = Localizer.getInstance();

        settings = new JButton(localizer.getLocalText("mainMenuSettingsLabel"));
        settings.setBorder(BorderFactory.createEmptyBorder());
        settings.setContentAreaFilled(false);
        settings.setActionCommand("icc-settings");
        settings.addActionListener(mainController);
        add(settings);

        //JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
        JButton sep = new JButton("|");
        sep.setMinimumSize(new Dimension(30, 40));
        sep.setMaximumSize(new Dimension(30, 40));
        sep.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        sep.setContentAreaFilled(false);
        add(sep);

        openAddServer = new JButton(localizer.getLocalText("mainMenuOpenOrAddServerLabel"));
        openAddServer.setBorder(BorderFactory.createEmptyBorder());
        openAddServer.setContentAreaFilled(false);
        openAddServer.setActionCommand("openoraddserver");
        openAddServer.addActionListener(mainController);
        add(openAddServer);

        add(Box.createHorizontalGlue());
    }
}
