package org.iotacontrolcenter.ui.proxy;

import org.iotacontrolcenter.dto.ActionResponse;
import org.iotacontrolcenter.dto.IccrIotaNeighborsPropertyDto;
import org.iotacontrolcenter.dto.IccrPropertyDto;
import org.iotacontrolcenter.dto.IccrPropertyListDto;
import org.iotacontrolcenter.ui.proxy.http.HttpProxy;

import java.util.Properties;

public class ServerProxy {

    private HttpProxy httpProxy;
    private Properties serverProps;

    public ServerProxy(Properties serverProps) {
        this.serverProps = serverProps;
        httpProxy = new HttpProxy(serverProps);
    }

    public void apiKeyChange(String newApiKey) {
        System.out.println("apiKeyChange: " + newApiKey);
        httpProxy.apiKeyChange(newApiKey);
    }

    public Properties iccrGetConfig() throws BadResponseException {
        Properties resp = new Properties();

        IccrPropertyListDto dto = httpProxy.iccrGetConfig();
        for(IccrPropertyDto prop : dto.getProperties()) {
            resp.setProperty(prop.getKey(), prop.getValue());
        }
            /*
iccrStartAtStartup->false
iccrStartIotaAtStartup->false
iccrStopIotaAtShutdown->false
iccrPortNumber->14266

iotaPortNumber->14265
iotaDownloadLink->http://85.93.93.110
iotaDownloadFilename->IRI-1.1.0.jar
iotaDir->/opt/iota
iotaStartCmd->java -jar IRI.jar
iotaNeighborRefreshTime->10
         */

        return resp;
    }

    public IccrIotaNeighborsPropertyDto iccrGetNbrsConfigProperty() throws BadResponseException {
        return httpProxy.iccrGetNbrsConfigProperty();
    }

    public void iccrSetConfig(Properties props) throws BadResponseException {
        httpProxy.iccrSetConfig(props);
    }

    public void iccrSetNbrsConfigProperty(IccrIotaNeighborsPropertyDto nbrs) throws BadResponseException {
        httpProxy.iccrUpdateIotaNbrs(nbrs);
    }

    public ActionResponse doIotaAction(String action, IccrPropertyListDto actionProps) throws BadResponseException {
        return httpProxy.doIotaAction(action, actionProps);
    }

}
