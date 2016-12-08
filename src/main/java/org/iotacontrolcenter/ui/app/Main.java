package org.iotacontrolcenter.ui.app;

import org.iotacontrolcenter.ui.controller.MainController;
import org.iotacontrolcenter.ui.panel.MainFrame;
import org.iotacontrolcenter.ui.properties.locale.Localizer;

import javax.swing.*;

public class Main {

    public static MainFrame mainFrame;
    public static boolean doSsl = true;
    static MainController mainController;

    public Main() {
    }

    public static void main(String[] args) {
        if(args != null && args.length > 0) {
            System.out.println("args length: " + args.length + ", args[0]: " + args[0]);
            if(args[0] != null && args[0].equals("nossl")) {
                doSsl = false;
            }
        }

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
        mainFrame.setTitle(Localizer.getInstance().getLocalText("mainWindowTitle"));
        mainFrame.pack();
        mainFrame.setVisible(true);

        SwingUtilities.invokeLater(() -> {
            mainController.initialPrompts();
        });
    }
}

