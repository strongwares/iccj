package org.iotacontrolcenter.ui.proxy;

import org.iotacontrolcenter.ui.proxy.http.HttpProxy;

import java.util.Properties;

public class ServerProxy {

    private HttpProxy httpProxy;
    private Properties serverProps;

    public ServerProxy(Properties serverProps) {
        this.serverProps = serverProps;
        httpProxy = new HttpProxy(serverProps);
    }

    public Properties iccrGetConfig() {
        Properties iccrProps = httpProxy.iccrGetConfig();

        return iccrProps;
    }
}
