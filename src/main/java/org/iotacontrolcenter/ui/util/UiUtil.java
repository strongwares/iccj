package org.iotacontrolcenter.ui.util;

import org.iotacontrolcenter.dto.IotaNeighborDto;
import org.iotacontrolcenter.dto.NeighborDto;
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

    public static boolean isSameNbr(NeighborDto nbr, IotaNeighborDto iotaNbr) {
        // The problem: iota nbr address is something like this:
        //         fred.com/93.188.173.198:14265
        //  or
        //         /10.0.0.0:14265
        // The nbr uri is something like this:
        //         udp://fred.com:14265
        //  or
        //         udp://10.0.0.0:14265
        String nbrUri = nbr.getUri();
        int addrSepIdx = nbrUri.indexOf("://");
        int portIdx = nbrUri.lastIndexOf(":");
        if(addrSepIdx < 0) {
            addrSepIdx = 0;
        }
        else {
            addrSepIdx += 3;
        }
        String nbrHost = nbrUri.substring(addrSepIdx, portIdx);
        String nbrPort = nbrUri.substring(portIdx);

        String iotaUri = iotaNbr.getAddress();
        portIdx = iotaUri.lastIndexOf(":");
        String iotaPort = iotaUri.substring(portIdx);
        addrSepIdx = iotaUri.indexOf("/");
        if(addrSepIdx < 0) {
            System.out.println("failed to find IOTA nbr host name address pieces");
            System.out.println("IOTA nbr address was: " + iotaUri);
            return false;
        }
        String iotaAddr1 = iotaUri.substring(0, addrSepIdx);
        String iotaAddr2 = iotaUri.substring(addrSepIdx+1, portIdx);

        boolean same = false;
        if(nbrPort != null) {
            same = nbrPort.equals(iotaPort);
        }
        else if(iotaPort != null) {
            same = iotaPort.equals(nbrPort);
        }
        else {
            same = true;
        }

        //System.out.println("nbrPort: " + nbrPort + ", iotaPort: " + iotaPort);
        //System.out.println("nbrHost: " + nbrHost + ", iotaAddr1: " + iotaAddr1 +
        //        ", iotaAddr2: " + iotaAddr2);

        if(!same) {
            System.out.println("not same nbr,  port differs");
            return same;
        }
        same = nbrHost.equalsIgnoreCase(iotaAddr1) ||
                nbrHost.equalsIgnoreCase(iotaAddr2);

        return same;

    }

    public static boolean promptUserYorN(String title, String msg) {
        int reply = JOptionPane.showConfirmDialog(Main.mainFrame, msg, title, JOptionPane.YES_NO_OPTION);
        return reply == JOptionPane.YES_OPTION;
    }

    public static final void showInfoDialog(String title, String msg) {
        JOptionPane.showMessageDialog(Main.mainFrame,  msg, title,  JOptionPane.INFORMATION_MESSAGE);
    }

    public static final void showErrorDialog(String title, String msg) {
        JOptionPane.showMessageDialog(Main.mainFrame,  msg, title,  JOptionPane.ERROR_MESSAGE);
    }

    public static boolean disValidIpV4(String ip) {
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

    public static boolean isValidPositiveNumber(String port) {
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
