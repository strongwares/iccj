package org.iotacontrolcenter.ui.proxy.http;


import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import java.util.Properties;

public class RequestHeaderFilter implements ClientRequestFilter {

    private Properties props = new Properties();

    public RequestHeaderFilter() {
    }

    public void setHeader(String key, String value) {
        System.out.println("setting header " + key + " -> " + value);
        props.setProperty(key, value);
    }

    public void addHeader(String key, String value) {
        System.out.println("adding header " + key + " -> " + value);
        props.setProperty(key, value);
    }

    @Override
    public void filter(ClientRequestContext context) {
        for (Object o : props.keySet()) {
            String key = (String)o;
            String val = props.getProperty(key);
            context.getHeaders().add(key, val);
        }
    }
}
