package org.iotacontrolcenter.ui.panel;


import javax.swing.*;

public class ServerTabPanel extends JTabbedPane {

    public ServerTabPanel() {
        super();
    }

    public void serverNameChange(String prevName, String newName) {
        for(int i = 0; i < getTabCount(); i++) {
            if(getTitleAt(i).equals(prevName)) {
                setTitleAt(i, newName);
                return;
            }
        }
        throw new IllegalStateException("Tab for server name " + prevName + " not found");
    }

    public boolean serverIsOpen(String name) {
        for(int i = 0; i < getTabCount(); i++) {
            if(getTitleAt(i).equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void removeServerTabByName(String name) {
        int toRemove = -1;
        for(int i = 0; i < getTabCount(); i++) {
            if(getTitleAt(i).equals(name)) {
                toRemove = i;
                break;
            }
        }
        if(toRemove >= 0) {
            remove(toRemove);
        }
        else {
            // TODO: localization
            throw new IllegalStateException("Tab for server name " + name + " not found");
        }
    }
}
