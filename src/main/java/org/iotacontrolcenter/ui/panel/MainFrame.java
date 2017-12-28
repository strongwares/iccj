package org.iotacontrolcenter.ui.panel;

import org.iotacontrolcenter.ui.controller.MainController;
import org.iotacontrolcenter.ui.menu.MainMenu;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = -7426735036400855096L;
    private MainController mainController;
    private MainMenu mainMenu;
    private MainPanel mainPanel;
    private ServerTabPanel serverTabPanel;

    public MainFrame(MainController mainController) {
        this.mainController = mainController;
    }

    public void prepareUi() {
        mainMenu = new MainMenu(mainController);
        setJMenuBar(mainMenu);

        serverTabPanel = new ServerTabPanel();
        serverTabPanel.setPreferredSize(new Dimension(1200, 460));
        mainController.setServerTabPanel(serverTabPanel);

        mainPanel = new MainPanel();
        mainPanel.add(serverTabPanel);

        mainController.init();
        getContentPane().add(mainPanel, BorderLayout.PAGE_START);
    }
}
