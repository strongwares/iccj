package org.iotacontrolcenter.ui.util;

import org.iotacontrolcenter.ui.app.Main;

import javax.swing.*;
import java.util.regex.Pattern;

public class UiUtil {

    private static Pattern ipV4Pattern;
    private static final String IPV4ADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    public static final void showErrorDialog(String title, String msg) {
        JOptionPane.showMessageDialog(Main.mainFrame,  msg, title,  JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean isValidIpV4(String ip) {
        if(ip == null || ip.isEmpty()) {
            return false;
        }
        if(ip.equalsIgnoreCase("localhost")) {
            return true;
        }
        if(ipV4Pattern == null) {
            ipV4Pattern = Pattern.compile(IPV4ADDRESS_PATTERN);
        }
        return ipV4Pattern.matcher(ip).matches();
    }

    public static boolean isValidPortNumber(String port) {
        try {
            Integer num = Integer.parseInt(port);
            return num > 0;
        }
        catch(Exception e) {
            return false;
        }
    }

    public static boolean isLocalhostIp(String ip) {
        return ip.equalsIgnoreCase("localhost") ||
                ip.equals("127.0.0.1");
    }

    public static String genServerId(String name) {
        return name.trim().replaceAll("[^A-Za-z0-9 ]", "");
    }
}
