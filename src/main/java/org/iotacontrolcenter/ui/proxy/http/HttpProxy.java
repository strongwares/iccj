package org.iotacontrolcenter.ui.proxy.http;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.iotacontrolcenter.dto.IccrPropertyDto;
import org.iotacontrolcenter.dto.IccrPropertyListDto;
import org.iotacontrolcenter.ui.properties.source.PropertySource;
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

    public Properties iccrGetConfig() {
        System.out.println("iccrGetConfig...");

        Properties props = new Properties();
        try {
            Response response = proxy.getConfigProperties();

            System.out.println("response status: " + response.getStatus());

            IccrPropertyListDto dto = response.readEntity(IccrPropertyListDto.class);

            for(IccrPropertyDto prop : dto.getProperties()) {
                System.out.println(prop.getKey() + "->" + prop.getValue());
                props.setProperty(prop.getKey(), prop.getValue());
            }

            response.close();
        }
        catch(Exception e) {
            System.out.println("iccrGetConfig exception: ");
            e.printStackTrace();
        }

        return props;
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

