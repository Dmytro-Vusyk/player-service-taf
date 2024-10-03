package com.companyname.testutils.listeners;

import com.companyname.config.PropertiesHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.xml.XmlSuite;

public class ThreadCountListener implements ITestListener {
    private static final Logger logger = LoggerFactory.getLogger(ThreadCountListener.class);

    @Override
    public void onStart(ITestContext context) {
        var threadProperty = (String) PropertiesHandler.getInstance()
                .getProjectProperties()
                .get("threadcount");
        int threadCount = Integer.parseInt(threadProperty);
        logger.debug("setting thread count:" + threadCount);
        context.getSuite().getXmlSuite().setParallel(XmlSuite.ParallelMode.METHODS);
        context.getSuite().getXmlSuite().setThreadCount(threadCount);
    }
}
