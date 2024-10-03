package com.companyname.config;

import com.companyname.enums.Environments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
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
        try (InputStream stream = this.getClass().getClassLoader().getResourceAsStream(path)) {
            if (stream != null){
                properties.load(stream);
            }
            logger.error("Failed to load properties. stream is null");
        } catch (IOException e) {
            logger.error("Failed to load properties caused by Error: { }", e);
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
        var env = getProjectProperties().getProperty("env");
        String folderName;
        if (env.equalsIgnoreCase(Environments.DEV.getValue())) {
            folderName = Environments.DEV.getValue();
        } else if (env.equalsIgnoreCase(Environments.INT.getValue())) {
            folderName = Environments.INT.getValue();
        } else if (env.equalsIgnoreCase(Environments.QA.getValue())) {
            folderName = Environments.QA.getValue();
        } else {
            folderName = Environments.DEV.getValue();
            logger.error("No environment {} found. Continue wth default env: {}" , env, Environments.DEV.getValue());
        }
        var path = Paths.get(CONFIG_FOLDER_PATH, ENV_FOLDER_PATH, folderName, ENV_PROPERTIES_NAME);
        var properties = getProperties(path.toString());
        setEnvProperties(properties);
    }

    private void loadSystemPropertiesForProject(){
        logger.info("Setting project properties as system properties");
        var props = new Properties();
        props.putAll(getProjectProperties());
        props.putAll(getEnvProperties());
        System.setProperties(props);
        logger.info("list of loaded system properties:\n" + System.getProperties());
    }

}
