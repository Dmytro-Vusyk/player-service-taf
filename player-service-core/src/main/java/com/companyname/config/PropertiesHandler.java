package com.companyname.config;

import com.companyname.enums.Environments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHandler {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesHandler.class);
    public static final String CONFIG_ENVIRONMENT_PROPERTIES = "config/env/%s/environment.properties";
    public static final String CONFIG_PROJECT_PROPERTIES = "config/project.properties";
    private static PropertiesHandler instance;
    private Properties projectProperties;
    private Properties envProperties;

    private PropertiesHandler() {
        loadProjectProperties();
        loadEnvProperties();
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

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new FileNotFoundException("Properties file not found at " + path);
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    private void loadProjectProperties() {
        var properties = getProperties(CONFIG_PROJECT_PROPERTIES);
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
            logger.error("No environment {} found. Continue wth default env: {}", env, Environments.DEV.getValue());
        }
        var properties = getProperties(String.format(CONFIG_ENVIRONMENT_PROPERTIES, folderName));
        setEnvProperties(properties);
    }
}
