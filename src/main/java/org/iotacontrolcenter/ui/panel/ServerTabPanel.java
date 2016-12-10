package org.iotacontrolcenter.ui.panel;

import javax.swing.*;
import java.awt.*;

public class ServerTabPanel extends JTabbedPane {

    public ServerTabPanel() {
        super();
    }

    public void enableIotaRefresh(boolean enable) {
        for(int i = 0; i < getTabCount(); i++) {
            ((ServerPanel) getComponentAt(i)).ctlr.enableIotaRefresh(enable);
        }
    }

    public void updateIotaRefresh() {
        for(int i = 0; i < getTabCount(); i++) {
            ((ServerPanel)getComponentAt(i)).ctlr.updateIotaRefresh();
        }
    }

    private Component getPanelByName(String name) {
        Component panel = null;
        for(int i = 0; i < getTabCount(); i++) {
            if(getTitleAt(i).equals(name)) {
                panel = getComponentAt(i);
                break;
            }
        }
        return panel;
    }

    public void serverApiKeyChange(String serverName, String newApiKey) {
        Component panel = getPanelByName(serverName);

        if(panel == null) {
            throw new IllegalStateException("API Key Change: panel for server name " + serverName + " not found");
        }
        ServerPanel serverPanel = ((ServerPanel) panel);
        serverPanel.ctlr.proxy.apiKeyChange(newApiKey);
    }

    public void serverNameChange(String prevName, String newName) {
        for(int i = 0; i < getTabCount(); i++) {
            if(getTitleAt(i).equals(prevName)) {
                setTitleAt(i, newName);
                return;
            }
        }
        throw new IllegalStateException("Name change: panel for server name " + prevName + " not found");
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
        Component panel = getPanelByName(name);

        if(panel == null) {
            throw new IllegalStateException("Remove server tab: panel for server name " + name + " not found");
        }
        ServerPanel serverPanel = ((ServerPanel) panel);
        serverPanel.ctlr.stopTimers();
        remove(panel);

        /*
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
            throw new IllegalStateException("Remove Server: panel for server name " + name + " not found");
        }
        */
    }

    public void setSelectedTabByName(String name) {
        int idxToSelect = -1;
        for(int i = 0; i < getTabCount(); i++) {
            if(getTitleAt(i).equals(name)) {
                idxToSelect = i;
            }
        }
        if(idxToSelect >= 0) {
            this.setSelectedIndex(idxToSelect);
        }
    }
}
