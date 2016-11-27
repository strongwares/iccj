package org.iotacontrolcenter.ui.properties.source;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.iotacontrolcenter.dto.IccrIotaNeighborsPropertyDto;
import org.iotacontrolcenter.dto.NeighborDto;

public class PropertySource {

    private static PropertySource instance;
    private static Object SYNC_INST = new Object();
    public static PropertySource getInstance() {
        synchronized (SYNC_INST) {
            if(PropertySource.instance == null) {
                PropertySource.instance = new PropertySource();
            }
            return PropertySource.instance;
        }
    }

    private static final Pattern PATTERN_TRUE = Pattern.compile("1|on|true|yes", Pattern.CASE_INSENSITIVE);
    private static final Pattern PATTERN_FALSE = Pattern.compile("0|off|false|no", Pattern.CASE_INSENSITIVE);
    private static final String CONF_FILE = "icc.properties";

    public static final String ICC_DIR_PROP = "iccDir";
    public static final String ICC_DIR_DEFAULT = "/opt/icc";

    private static final String LOC_COUNTRY_PROP = "iccCountryLocale";
    private static final String LOC_LANG_PROP = "iccLanguageLocale";
    private static final String LOC_LANG_DEFAULT = "en";
    private static final String LOC_COUNTRY_DEFAULT = "US";

    private Properties props;
    private String confDir;
    private String confFile;
    private String iccDir;
    private String osName;
    private PropertiesConfiguration propWriter;
    private DateTimeFormatter ymdhmsFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private PropertySource() {
        System.out.println("new PropertySource");

        osName = System.getProperty("os.name").toLowerCase();

        iccDir = System.getProperty(ICC_DIR_PROP);
        if(iccDir == null || iccDir.isEmpty()) {
            System.out.println(ICC_DIR_PROP + " system setting not available, using default: " + ICC_DIR_DEFAULT);
            iccDir = ICC_DIR_DEFAULT;
        }

        confDir = iccDir + "/conf";
        confFile = confDir + "/" + CONF_FILE;

        try {
            propWriter = new PropertiesConfiguration(confFile);
        }
        catch(Exception e) {
            System.out.println("PropertySource exception creating PropertiesConfiguration: " + e.getLocalizedMessage());
        }

        props = new Properties();
        load();
    }

    public void load() {
        try {
            InputStream is = new FileInputStream(confFile);
            props.load(is);
        }
        catch(Exception e) {
            System.out.println("failed to load icc.properties from " + confDir);
            e.printStackTrace();
        }
    }

    public String getNowDateTimestamp() {
        return ymdhmsFormatter.format(LocalDateTime.now());
    }

    public String getIccConfDir() {
        return confDir;
    }

    public String getIccDir() {
        return iccDir;
    }

    public String getOsName() {
        return osName;
    }

    public boolean osIsWindows() {
        return (osName.indexOf("win") >= 0);
    }

    public boolean osIsMax() {
        return (osName.indexOf("mac") >= 0);
    }

    public void setProperty(String key, Object value) {
        props.setProperty(key, (String)value);
        propWriter.setProperty(key, value);

        try {
            propWriter.save();
        }
        catch(Exception e) {
            System.out.println("PropertySource exception saving PropertiesConfiguration: " + e.getLocalizedMessage());
        }
    }

    public String getLocaleLanguage() {
        String val = getString(LOC_LANG_PROP);
        if(val == null || val.isEmpty()) {
            val = LOC_LANG_DEFAULT;
        }
        return val;
    }

    public String getLocaleCountry() {
        String val = getString(LOC_COUNTRY_PROP);
        if(val == null || val.isEmpty()) {
            val = LOC_COUNTRY_DEFAULT;
        }
        return val;
    }

    public String getString(String key) {
        return props.getProperty(key);
    }

    public boolean getBoolean(String key) {
        String val = props.getProperty(key);
        if(val != null) {
            if(PATTERN_TRUE.matcher(val).matches()) {
                return true;
            }
            else if(PATTERN_FALSE.matcher(val).matches()) {
                return false;
            }
            else {
                throw new IllegalArgumentException("Invalid boolean value provided for " + key);
            }
        }
        throw new IllegalArgumentException("No value provided for " + key);
    }

    public int getInteger(String key) {
        String val = props.getProperty(key);
        if(val != null) {
            try {
                return Integer.parseInt(val);
            }
            catch(NumberFormatException nfe) {
                throw new IllegalArgumentException("Invalid integer value provided for " + key);
            }
        }
        throw new IllegalArgumentException("No value provided for " + key);
    }

    public List<String> getList(String key) {
        List<String> keys = new ArrayList<>();
        String val = getString(key);
        if(val != null && !val.isEmpty()) {
            if(val.contains(",")) {
                String[] arr = val.split(",");
                for(String id : arr) {
                    keys.add(id.trim());
                }
            }
            else {
                keys.add(val);
            }
        }
        return keys;
    }
}
