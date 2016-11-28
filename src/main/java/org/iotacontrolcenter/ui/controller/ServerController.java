package org.iotacontrolcenter.ui.controller;


import org.iotacontrolcenter.ui.proxy.ServerProxy;

public class ServerController {

    private ServerProxy proxy;

    public ServerController(ServerProxy proxy) {
        this.proxy = proxy;
    }
}
