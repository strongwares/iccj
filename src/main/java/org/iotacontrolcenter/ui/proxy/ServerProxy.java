package org.iotacontrolcenter.ui.proxy;

import java.util.List;
import java.util.Properties;

import org.iotacontrolcenter.dto.ActionResponse;
import org.iotacontrolcenter.dto.IccrIotaNeighborsPropertyDto;
import org.iotacontrolcenter.dto.IccrPropertyDto;
import org.iotacontrolcenter.dto.IccrPropertyListDto;
import org.iotacontrolcenter.dto.LogLinesResponse;
import org.iotacontrolcenter.ui.proxy.http.HttpProxy;

public class ServerProxy {

    private HttpProxy httpProxy;
    @SuppressWarnings("unused")
    private Properties serverProps;

    public ServerProxy(Properties serverProps) {
        this.serverProps = serverProps;
        httpProxy = new HttpProxy(serverProps);
    }

    public void iccrPortNumberChange(String newPortNumber) {
        httpProxy.iccrPortNumberChange(newPortNumber);
    }

    public void apiKeyChange(String newApiKey) {
        //System.out.println("apiKeyChange: " + newApiKey);
        httpProxy.apiKeyChange(newApiKey);
    }

    public List<String> getIccrEventLog() throws BadResponseException {
        return httpProxy.getIccrEventLog();
    }

    public void deleteIccrEventLog() throws BadResponseException {
        httpProxy.deleteIccrEventLog();
    }

    public LogLinesResponse getIotaLog(String fileDirection, Long numLines, Long lastFileLength, Long lastFilePosition) throws BadResponseException {
        return httpProxy.getIotaLog(fileDirection, numLines, lastFileLength, lastFilePosition);
    }

    public Properties iccrGetConfig() throws BadResponseException {
        Properties resp = new Properties();

        IccrPropertyListDto dto = httpProxy.iccrGetConfig();
        for(IccrPropertyDto prop : dto.getProperties()) {
            if (prop.getKey() == null || prop.getValue() == null) {
                continue;
            }
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

    public ActionResponse doIccrAction(String action, IccrPropertyListDto actionProps) throws BadResponseException {
        return httpProxy.doIccrAction(action, actionProps);
    }

}
