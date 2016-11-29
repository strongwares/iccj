package org.iotacontrolcenter.ui.app;

import org.iotacontrolcenter.ui.controller.MainController;
import org.iotacontrolcenter.ui.panel.MainFrame;

import javax.swing.*;

public class Main {

    static MainFrame mainFrame;
    static MainController mainController;

    public Main() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGui());
    }

    public static void createAndShowGui() {

        try {
            /*
            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName());
            */
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch(Exception e) {
            System.out.println("Exception setting L&F: " + e);
        }

        mainController = new MainController();
        mainFrame = new MainFrame(mainController);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.prepareUi();
        mainFrame.setSize(800, 400);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}

