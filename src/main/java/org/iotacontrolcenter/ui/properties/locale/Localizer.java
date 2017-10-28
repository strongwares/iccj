package org.iotacontrolcenter.ui.properties.locale;

import org.iotacontrolcenter.ui.properties.source.PropertySource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class Localizer {

    private static Localizer instance;
    private static Object SYNC_INST = new Object();

    public static Localizer getInstance() {
        synchronized (SYNC_INST) {
            if (Localizer.instance == null) {
                Localizer.instance = new Localizer();
            }
            return Localizer.instance;
        }
    }

    @SuppressWarnings("unused")
    private String confDir;
    private String country;
    private Locale defaultLoc;
    private ResourceBundle defLocText;
    private DateTimeFormatter eventDtf;
    private String lang;
    private Locale loc;
    private ResourceBundle locText;
    private PropertySource propSource;

    private Localizer() {
        System.out.println("new Localizer");

        propSource = PropertySource.getInstance();

        lang = propSource.getLocaleLanguage();
        country = propSource.getLocaleCountry();
        loc = new Locale(lang, country);

        eventDtf = DateTimeFormatter.ISO_LOCAL_DATE_TIME.withLocale(loc);

        try {
            // Need to load the resource bundle prop file from the ICC conf dir
            // From specific path:
            //File file = new File(confDir);
            //URL[] urls = {file.toURI().toURL()};
            //ClassLoader loader = new URLClassLoader(urls);
            //locText = ResourceBundle.getBundle("MessagesBundle", loc, loader);

            // From classpath
            locText = ResourceBundle.getBundle("MessagesBundle", loc);

            defaultLoc = new Locale("en", "US");

            // From specific path:
            //defLocText = ResourceBundle.getBundle("MessagesBundle", defaultLoc, loader);

            // From classpath
            defLocText = ResourceBundle.getBundle("MessagesBundle", defaultLoc);
        } catch (Exception e) {
            System.out.println("Localizer exception: " + e.getLocalizedMessage());
        }
    }

    public String getEventTime() {
        return eventDtf.format(LocalDateTime.now());
    }

    public String getLocalTextWithFixed(String key, String fixed) {
        return getLocalText(key) + " " + fixed;
    }

    public String getFixedWithLocalText(String fixed, String key) {
        return fixed + getLocalText(key);
    }

    public String getLocalText(String key) {
        String txt = null;
        try {
            txt = locText.getString(key);
            if (txt == null || txt.isEmpty()) {
                txt = defLocText.getString(key);
                if (txt == null || txt.isEmpty()) {
                    txt = key;
                }
            }
        } catch (Exception e) {
            System.out.println("getLocalText exception: " + e.getLocalizedMessage());
            txt = key;
        }
        return txt;
    }
}
