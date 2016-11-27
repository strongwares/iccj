package org.iotacontrolcenter.ui.app;

import javax.swing.*;

public class Main {

    public Main() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new Main();
                }
            });
    }
}

