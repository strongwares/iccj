package org.iotacontrolcenter.ui.app;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.DefaultEditorKit;

import org.iotacontrolcenter.ui.controller.MainController;
import org.iotacontrolcenter.ui.panel.MainFrame;
import org.iotacontrolcenter.ui.properties.locale.Localizer;
import org.iotacontrolcenter.ui.util.UiUtil;

public class Main {

    public static MainFrame mainFrame;
    public static List<Image> icons = new ArrayList<>();
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

        try {
            String osName = System.getProperty("os.name", "").toLowerCase();
            System.out.println("OS name: " + osName);
            if (osName.startsWith("mac")) {
                InputMap im = (InputMap) UIManager.get("TextField.focusInputMap");
                im.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.META_DOWN_MASK), DefaultEditorKit.copyAction);
                im.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.META_DOWN_MASK), DefaultEditorKit.pasteAction);
                im.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.META_DOWN_MASK), DefaultEditorKit.cutAction);
                im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.META_DOWN_MASK), DefaultEditorKit.selectAllAction);

                im = (InputMap) UIManager.get("TextArea.focusInputMap");
                im.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.META_DOWN_MASK), DefaultEditorKit.copyAction);
                im.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.META_DOWN_MASK), DefaultEditorKit.pasteAction);
                im.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.META_DOWN_MASK), DefaultEditorKit.cutAction);
                im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.META_DOWN_MASK), DefaultEditorKit.selectAllAction);
            }
        }
        catch(Exception e) {
            System.out.println("Exception checking for os.name: " + e);
        }

        mainController = new MainController();
        mainFrame = new MainFrame(mainController);

        icons.add(UiUtil.loadIcon(Constants.IMAGE_ICON_FILENAME_MAIN_1_APP).getImage());
        icons.add(UiUtil.loadIcon(Constants.IMAGE_ICON_FILENAME_MAIN_2_APP).getImage());
        icons.add(UiUtil.loadIcon(Constants.IMAGE_ICON_FILENAME_MAIN_3_APP).getImage());
        mainFrame.setIconImages(icons);

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

