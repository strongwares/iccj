package org.iotacontrolcenter.ui.proxy.http;

import org.apache.http.HttpStatus;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.iotacontrolcenter.dto.IccrPropertyDto;
import org.iotacontrolcenter.dto.IccrPropertyListDto;
import org.iotacontrolcenter.dto.SimpleResponse;
import org.iotacontrolcenter.ui.properties.source.PropertySource;
import org.iotacontrolcenter.ui.proxy.BadResponseException;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient4Engine;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.Properties;

public class HttpProxy {

    private static final int MAX_TOTAL_CONN = 20;
    private static final int MAX_TOTAL_CONN_PER_ROUTE = 20;

    private ResteasyClient client;
    private PoolingHttpClientConnectionManager connectionManager;
    private ApacheHttpClient4Engine engine;
    private CloseableHttpClient httpClient;
    private String path;
    private IccrService proxy;
    private Properties serverProps;
    private ResteasyWebTarget target;

    public HttpProxy(Properties serverProps) {
        this.serverProps = serverProps;
        setUp();
    }

    public IccrPropertyListDto iccrGetConfig() throws BadResponseException {
        System.out.println("iccrGetConfig...");
        IccrPropertyListDto dto = null;
        Response response = null;
        try {
            response = proxy.getConfigProperties();

            System.out.println("response status: " + response.getStatus());

            if(response.getStatus() == HttpStatus.SC_OK) {
                dto = response.readEntity(IccrPropertyListDto.class);
            }
            else {
                throw new BadResponseException("iccrGetConfigError",
                        response.readEntity(SimpleResponse.class));
            }

        }
        catch(Exception e) {
            System.out.println("iccrGetConfig exception: ");
            e.printStackTrace();
        }
        finally {
            if(response != null) {
                response.close();
            }
        }
        return dto;
    }

    public void iccrSetConfig(Properties props) throws BadResponseException {
        System.out.println("iccrSetConfig...");

        for(Object o : props.keySet()) {
            String key = (String)o;
            String val = props.getProperty(key);
            System.out.println("setting " + key + " -> " + val);

            Response response =  null;
            try {
                response = proxy.updateConfigProperty(key, new IccrPropertyDto(key, val));

                System.out.println("response status: " + response.getStatus());

                if(response.getStatus() != HttpStatus.SC_OK) {
                    throw new BadResponseException("iccrSetConfigError",
                            response.readEntity(SimpleResponse.class));
                }
            }
            catch(Exception e) {
                System.out.println("iccrSetConfig exception: ");
                e.printStackTrace();

                throw new BadResponseException("iccrSetConfigError",
                        new SimpleResponse(false, e.getLocalizedMessage()));
            }
            finally {
                if(response != null) {
                    response.close();
                }
            }
        }
    }

    public void doIotaAction(String action) {
        System.out.println("doIotaAction " + action + "...");
        Response response = null;
        try {
            response = proxy.doIotaAction(action);

            System.out.println("response status: " + response.getStatus());

            if(response.getStatus() != HttpStatus.SC_OK) {
                throw new BadResponseException("iotaAction" + action,
                        response.readEntity(SimpleResponse.class));
            }

        }
        catch(Exception e) {
            System.out.println("iotaAction" + action  + " exception: ");
            e.printStackTrace();
        }
        finally {
            if(response != null) {
                response.close();
            }
        }
    }

    public void startIota() {
    }

    public void stopIota() {
    }

    public void deleteIotaDb() {
    }

    public void deleteIota() {
    }

    private String buildPath() {
        String serverUrl = "http://" + serverProps.getProperty(PropertySource.SERVER_IP_PROP) +
                ":" + serverProps.getProperty(PropertySource.SERVER_ICCR_PORT_NUM_PROP);

        return serverUrl;
    }

    private void setUp() {
        path = buildPath();

        connectionManager = new PoolingHttpClientConnectionManager();
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
        connectionManager.setMaxTotal(MAX_TOTAL_CONN);
        connectionManager.setDefaultMaxPerRoute(MAX_TOTAL_CONN_PER_ROUTE);
        engine = new ApacheHttpClient4Engine(httpClient);

        client = new ResteasyClientBuilder().httpEngine(engine).build();
        target = client.target(UriBuilder.fromPath(path));
        proxy = target.proxy(IccrService.class);
    }
}

