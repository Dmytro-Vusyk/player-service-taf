package com.companyname.config;

import com.companyname.enums.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesHandler {
    private final Logger logger = LoggerFactory.getLogger(PropertiesHandler.class);
    private static final String PROJECT_PROPERTIES_NAME = "project.properties";
    private static final String CONFIG_FOLDER_PATH = "config";
    private static final String ENV_FOLDER_PATH = "env";
    private static final String ENV_PROPERTIES_NAME = "environment.properties";
    private static PropertiesHandler instance;
    private Properties projectProperties;
    private Properties envProperties;

    private PropertiesHandler() {
        loadProjectProperties();
        loadEnvProperties();
        loadSystemPropertiesForProject();
    }

    public Properties getProjectProperties() {
        return projectProperties;
    }

    private void setProjectProperties(Properties projectProperties) {
        this.projectProperties = projectProperties;
    }

    public Properties getEnvProperties() {
        return envProperties;
    }

    private void setEnvProperties(Properties envProperties) {
        this.envProperties = envProperties;
    }

    public static PropertiesHandler getInstance() {
        if (instance == null) {
            instance = new PropertiesHandler();
        }
        return instance;
    }

    private Properties getProperties(String path) {
        logger.info("Get properties from path: {}", path);
        Properties properties = new Properties();
        try (FileInputStream stream = new FileInputStream(getConfigFileFromResources(path))) {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    private void loadProjectProperties() {
        Path path = Paths.get(CONFIG_FOLDER_PATH, PROJECT_PROPERTIES_NAME);
        var properties = getProperties(path.toString());
        setProjectProperties(properties);
    }

    private void loadEnvProperties() {
        Path projecConfigPath = Paths.get(CONFIG_FOLDER_PATH, PROJECT_PROPERTIES_NAME);
        var env = getProperties(projecConfigPath.toString()).getProperty("env");
        String folderName;
        if (env.equalsIgnoreCase(Environment.DEV.getValue())) {
            folderName = Environment.DEV.getValue();
        } else if (env.equalsIgnoreCase(Environment.INT.getValue())) {
            folderName = Environment.INT.getValue();
        } else if (env.equalsIgnoreCase(Environment.QA.getValue())) {
            folderName = Environment.QA.getValue();
        } else {
            folderName = Environment.DEV.getValue();
            logger.error("No environment {} found. Continue wth default env: {}" , env, Environment.DEV.getValue());
        }
        var path = Paths.get(CONFIG_FOLDER_PATH, ENV_FOLDER_PATH, folderName, ENV_PROPERTIES_NAME);
        var properties = getProperties(path.toString());
        setEnvProperties(properties);
    }

    private File getConfigFileFromResources(String path) {
        var res = getClass().getClassLoader().getResource(path);
        File file = null;
        if (res != null) {
            try {
                file = new File(res.toURI());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        } else {
            //TODO: add exception here
        }
        return file;
    }

    private void loadSystemPropertiesForProject(){
        logger.info("Setting project properties as system properties");
        var props = new Properties();
        props.putAll(getProjectProperties());
        props.putAll(getEnvProperties());
        System.setProperties(props);
        logger.info("list of loaded system properties:" + "\n" + System.getProperties());
    }

}
