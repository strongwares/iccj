package org.iotacontrolcenter.ui.proxy.http;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpStatus;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.iotacontrolcenter.dto.*;
import org.iotacontrolcenter.ui.app.Main;
import org.iotacontrolcenter.ui.properties.source.PropertySource;
import org.iotacontrolcenter.ui.proxy.BadResponseException;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient4Engine;

import javax.net.ssl.*;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class HttpProxy {

    private static final int MAX_TOTAL_CONN = 20;
    private static final int MAX_TOTAL_CONN_PER_ROUTE = 20;

    private ResteasyClient client;
    private PoolingHttpClientConnectionManager connectionManager;
    private ApacheHttpClient4Engine engine;
    private RequestHeaderFilter headerFilter;
    private String path;
    private IccrService proxy;
    private Properties serverProps;
    private ResteasyWebTarget target;

    public HttpProxy(Properties serverProps) {
        this.serverProps = serverProps;
        setUp();
    }

    public void apiKeyChange(String newApiKey) {
        headerFilter.setHeader(serverProps.getProperty(PropertySource.SERVER_ICCR_API_KEY_HEADER_NAME_PROP), newApiKey);
    }

    public void deleteIccrEventLog() throws BadResponseException {
        System.out.println("deleteIccrEventLog...");
        Response response = null;
        try {
            response = proxy.deleteIccrEventLog();

            System.out.println("response status: " + response.getStatus());

            if(response.getStatus() == HttpStatus.SC_OK) {
                SimpleResponse sr = response.readEntity(SimpleResponse.class);
            }
            else {
                throw new BadResponseException("deleteIccrEventLog",
                        response.readEntity(SimpleResponse.class));
            }

        }
        catch(BadResponseException bre) {
            System.out.println("deleteIccrEventLog bad response exception: ");
            bre.printStackTrace();
            throw bre;
        }
        catch(Exception e) {
            System.out.println("deleteIccrEventLog exception: ");
            e.printStackTrace();

            Throwable cause = e.getCause();
            String msg;
            if(cause != null) {
                msg = cause.getLocalizedMessage();
            }
            else {
                msg = e.getLocalizedMessage();
            }
            throw new BadResponseException("deleteIccrEventLog",
                    new SimpleResponse(false, msg));
        }
        finally {
            if(response != null) {
                response.close();
            }
        }
    }

    public List<String> getIccrEventLog() throws BadResponseException {
        System.out.println("getIccrEventLog...");
        List<String> log = null;
        Response response = null;
        try {
            response = proxy.getIccrEventLog();

            System.out.println("response status: " + response.getStatus());

            if(response.getStatus() == HttpStatus.SC_OK) {
                log = response.readEntity(List.class);
            }
            else {
                throw new BadResponseException("getIccrEventLog",
                        response.readEntity(SimpleResponse.class));
            }

        }
        catch(BadResponseException bre) {
            throw bre;
        }
        catch(Exception e) {
            System.out.println("getIccrEventLog exception: ");
            e.printStackTrace();

            Throwable cause = e.getCause();
            String msg;
            if(cause != null) {
                msg = cause.getLocalizedMessage();
            }
            else {
                msg = e.getLocalizedMessage();
            }
            throw new BadResponseException("getIccrEventLogError",
                    new SimpleResponse(false, msg));
        }
        finally {
            if(response != null) {
                response.close();
            }
        }
        return log;
    }

    public LogLinesResponse getIotaLog(String fileDirection, Long numLines, Long lastFileLength, Long lastFilePosition) throws BadResponseException {
        System.out.println("getIotaLog(" + fileDirection + ", " + numLines + ", " + lastFilePosition);
        LogLinesResponse llr = null;
        Response response = null;
        try {
            response = proxy.getIotaLog(fileDirection, numLines, lastFileLength, lastFilePosition);

            System.out.println("response status: " + response.getStatus());

            if(response.getStatus() == HttpStatus.SC_OK) {
                llr = response.readEntity(LogLinesResponse.class);
            }
            else {
                throw new BadResponseException("getIotaLog",
                        response.readEntity(SimpleResponse.class));
            }

        }
        catch(BadResponseException bre) {
            throw bre;
        }
        catch(Exception e) {
            System.out.println("getIotaLog exception: ");
            e.printStackTrace();

            Throwable cause = e.getCause();
            String msg;
            if(cause != null) {
                msg = cause.getLocalizedMessage();
            }
            else {
                msg = e.getLocalizedMessage();
            }
            throw new BadResponseException("getIotaLogError",
                    new SimpleResponse(false, msg));
        }
        finally {
            if(response != null) {
                response.close();
            }
        }
        return llr;
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
        catch(BadResponseException bre) {
            throw bre;
        }
        catch(Exception e) {
            System.out.println("iccrGetConfig exception: ");
            e.printStackTrace();

            Throwable cause = e.getCause();
            String msg;
            if(cause != null) {
                msg = cause.getLocalizedMessage();
            }
            else {
                msg = e.getLocalizedMessage();
            }
            throw new BadResponseException("iccrGetConfigError",
                    new SimpleResponse(false, msg));
        }
        finally {
            if(response != null) {
                response.close();
            }
        }
        return dto;
    }

    public IccrPropertyDto iccrGetConfigProperty(String key) throws BadResponseException {
        System.out.println("iccrGetConfigProperty...");
        IccrPropertyDto dto = null;
        Response response = null;
        try {
            response = proxy.getConfigProperty(key);

            System.out.println("response status: " + response.getStatus());

            if(response.getStatus() == HttpStatus.SC_OK) {
                dto = response.readEntity(IccrPropertyDto.class);
            }
            else {
                throw new BadResponseException("iccrGetConfigPropertyError",
                        response.readEntity(SimpleResponse.class));
            }

        }
        catch(BadResponseException bre) {
            throw bre;
        }
        catch(Exception e) {
            System.out.println("iccrGetConfigProperty exception: ");
            e.printStackTrace();

            Throwable cause = e.getCause();
            String msg;
            if(cause != null) {
                msg = cause.getLocalizedMessage();
            }
            else {
                msg = e.getLocalizedMessage();
            }
            throw new BadResponseException("iccrGetConfigPropertyError",
                    new SimpleResponse(false, msg));
        }
        finally {
            if(response != null) {
                response.close();
            }
        }
        return dto;
    }

    public IccrIotaNeighborsPropertyDto iccrGetNbrsConfigProperty() throws BadResponseException {
        System.out.println("iccrGetNbrsProperty...");
        IccrIotaNeighborsPropertyDto dto = null;
        Response response = null;
        try {
            response = proxy.getIotaNbrsConfig();

            System.out.println("response status: " + response.getStatus());

            if(response.getStatus() == HttpStatus.SC_OK) {
                dto = response.readEntity(IccrIotaNeighborsPropertyDto.class);
            }
            else {
                throw new BadResponseException("iccrGetNbrsConfigPropertyError",
                        response.readEntity(SimpleResponse.class));
            }

        }
        catch(BadResponseException bre) {
            throw bre;
        }
        catch(Exception e) {
            System.out.println("iccrGetNbrsProperty exception: ");
            e.printStackTrace();

            Throwable cause = e.getCause();
            String msg;
            if(cause != null) {
                msg = cause.getLocalizedMessage();
            }
            else {
                msg = e.getLocalizedMessage();
            }
            throw new BadResponseException("iccrGetNbrsConfigPropertyError",
                    new SimpleResponse(false, msg));
        }
        finally {
            if(response != null) {
                response.close();
            }
        }
        return dto;
    }

    public List<SimpleResponse> iccrSetConfig(Properties props) throws BadResponseException {
        System.out.println("iccrSetConfig...");

        List<SimpleResponse> dtos = new ArrayList<>();

        for(Object o : props.keySet()) {
            String key = (String)o;
            String val = props.getProperty(key);

            System.out.println("setting " + key + " -> " + val);

            Response response =  null;
            SimpleResponse dto = null;
            try {
                response = proxy.updateConfigProperty(key, new IccrPropertyDto(key, val));

                System.out.println("response status: " + response.getStatus());

                if(response.getStatus() != HttpStatus.SC_OK) {
                    throw new BadResponseException("iccrSetConfigError",
                            response.readEntity(SimpleResponse.class));
                }
                else {
                    dtos.add(response.readEntity(SimpleResponse.class));
                }
            }
            catch(BadResponseException bre) {
                throw bre;
            }
            catch(Exception e) {
                System.out.println("iccrSetConfig exception: ");
                e.printStackTrace();

                Throwable cause = e.getCause();
                String msg;
                if(cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                else {
                    msg = e.getLocalizedMessage();
                }

                throw new BadResponseException("iccrSetConfigError",
                        new SimpleResponse(false, msg));
            }
            finally {
                if(response != null) {
                    response.close();
                }
            }
        }
        return dtos;
    }

    public SimpleResponse iccrUpdateIotaNbrs(IccrIotaNeighborsPropertyDto nbrs) throws BadResponseException {
        System.out.println("iccrUpdateIotaNbrs...");

        Response response = null;
            SimpleResponse dto = null;
            try {
                response = proxy.updateIotaNbrsConfig(nbrs);

                System.out.println("response status: " + response.getStatus());

                dto = response.readEntity(SimpleResponse.class);
                if(response.getStatus() != HttpStatus.SC_OK) {
                    throw new BadResponseException("iccrSetNbrsConfigError", dto);
                }
            }
            catch(BadResponseException bre) {
                throw bre;
            }
            catch(Exception e) {
                System.out.println("iccrUpdateIotaNbrs exception: ");
                e.printStackTrace();

                Throwable cause = e.getCause();
                String msg;
                if(cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                else {
                    msg = e.getLocalizedMessage();
                }

                throw new BadResponseException("iccrSetNbrsConfigError",
                        new SimpleResponse(false, msg));
            }
            finally {
                if(response != null) {
                    response.close();
                }
            }

        return dto;
    }


    public ActionResponse doIotaAction(String action, IccrPropertyListDto actionProps)  throws BadResponseException {
        System.out.println("doIotaAction " + action + "...");
        Response response = null;
        ActionResponse dto = null;
        try {
            if(actionProps == null) {
                actionProps = new IccrPropertyListDto();
            }

            System.out.println("actionProps: " + actionProps);

            response = proxy.doIotaAction(action, actionProps);

            System.out.println("response status: " + response.getStatus());

            if(response.getStatus() != HttpStatus.SC_OK) {

                SimpleResponse simple = response.readEntity(SimpleResponse.class);

                System.out.println("iota action " + action + " failed: " + simple.getMsg());

                throw new BadResponseException(action + "IotaActionError",
                        simple);
            }
            dto = response.readEntity(ActionResponse.class);
        }
        catch(BadResponseException bre) {
            System.out.println("Caught BRE, rethrowing");
            throw bre;
        }
        catch(Exception e) {
            System.out.println("iotaAction" + action  + " exception: ");
            e.printStackTrace();

            Throwable cause = e.getCause();
            String msg;
            if(cause != null) {
                msg = cause.getLocalizedMessage();
            }
            else {
                msg = e.getLocalizedMessage();
            }

            throw new BadResponseException(action + "IotaActionError",
                    new SimpleResponse(false, msg));
        }
        finally {
            if(response != null) {
                response.close();
            }
        }
        return dto;
    }

    private String buildPath() {
        int portNum = Integer.valueOf(serverProps.getProperty(PropertySource.SERVER_ICCR_PORT_NUM_PROP));
        String scheme = Main.doSsl ? "https" : "http";
        //int port = Main.doSsl ?  portNum + 1 : portNum;
        int port = portNum;
        String serverUrl = scheme + "://" + serverProps.getProperty(PropertySource.SERVER_IP_PROP) +
                ":" + String.valueOf(port);

        System.out.println("new server address: " + serverUrl);

        return serverUrl;
    }

    private void setUp() {
        path = buildPath();

        SSLConnectionSocketFactory sslsf = null;

        //HostnameVerifier allHostsValid = null;

        try {
            // My own identity:
            //KeyStore ks = KeyStore.getInstance("jks");
            //ks.load(this.getClass().getResourceAsStream("/iccj-ks.jks"), "secret".toCharArray());

            // Trust own CA and all self-signed certs

            //FileInputStream fis = new FileInputStream()
            //InputStream is = this.getClass().getResourceAsStream("/iccj-ts.jks"));

            // A trust store:
            //File tsFile = new File(this.getClass().getResource("/iccj-ts.jks").toURI());

            TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
                public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                public void checkServerTrusted(X509Certificate[] certs, String authType) { }
            } };

            /*
            SSLContext dsslContext = SSLContext.getInstance("SSL");
            dsslContext.init(
                    null, trustAllCerts, new SecureRandom()
            );
            */

            SSLContext sslContext = SSLContexts.custom()
                    .loadTrustMaterial(new TrustStrategy() {
                        @Override
                        public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                            return true;
                        }
                    })
                    .setSecureRandom(new SecureRandom())
                    .build();

            /*
            allHostsValid = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            */

            // Allow TLSv1 protocol only with all trusting hostname verifier
            sslsf = new SSLConnectionSocketFactory(
                    sslContext,
                    new String[]{"TLSv1"},
                    null,
                    NoopHostnameVerifier.INSTANCE);

        }
        catch(Exception e) {
            System.out.println("SSL context exception: ");
            e.printStackTrace();
        }

        boolean doSsl = Main.doSsl && sslsf != null;

        final Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new PlainConnectionSocketFactory())
                .register("https", sslsf)
                .build();

        connectionManager = new PoolingHttpClientConnectionManager(registry);

        CloseableHttpClient httpClient = doSsl  ?
                HttpClients.custom().setConnectionManager(connectionManager)
                        .setSSLSocketFactory(sslsf).setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build() :
                HttpClients.custom().setConnectionManager(connectionManager).build();

        connectionManager.setMaxTotal(MAX_TOTAL_CONN);
        connectionManager.setDefaultMaxPerRoute(MAX_TOTAL_CONN_PER_ROUTE);
        engine = new ApacheHttpClient4Engine(httpClient);

        client = new ResteasyClientBuilder().httpEngine(engine).build();

        headerFilter = new RequestHeaderFilter();
        headerFilter.addHeader(serverProps.getProperty(PropertySource.SERVER_ICCR_API_KEY_HEADER_NAME_PROP),
                serverProps.getProperty(PropertySource.SERVER_ICCR_API_KEY_PROP));

        client.register(headerFilter);

        target = client.target(UriBuilder.fromPath(path));

        proxy = target.proxy(IccrService.class);
    }
}

