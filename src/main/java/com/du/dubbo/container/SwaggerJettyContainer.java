package com.du.dubbo.container;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.alibaba.dubbo.common.utils.NetUtils;
import com.alibaba.dubbo.container.Container;
import com.alibaba.dubbo.container.jetty.JettyContainer;
import com.du.dubbo.servlet.SwaggerServlet;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.ServletHandler;
import org.mortbay.jetty.servlet.ServletHolder;

public class SwaggerJettyContainer implements Container {
    private static final Logger logger = LoggerFactory.getLogger(JettyContainer.class);
    public static final String SWAGGER_JETTY_PORT = "dubbo.swaggerjetty.port";
    public static final int DEFAULT_JETTY_PORT = 8080;
    SelectChannelConnector connector;

    @Override
    public void start() {
        String serverPort = ConfigUtils.getProperty(SWAGGER_JETTY_PORT);
        int port;
        if (serverPort == null || serverPort.length() == 0) {
            port = DEFAULT_JETTY_PORT;
        } else {
            port = Integer.parseInt(serverPort);
        }
        connector = new SelectChannelConnector();
        connector.setPort(port);
        ServletHandler handler = new ServletHandler();


        ServletHolder pageHolder = handler.addServletWithMapping(SwaggerServlet.class, "/*");
        pageHolder.setInitOrder(2);

        Server server = new Server();
        server.addConnector(connector);
        server.addHandler(handler);
        try {
            server.start();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to start jetty server on " + NetUtils.getLocalHost() + ":" + port + ", cause: " + e.getMessage(), e);
        }
    }

    @Override
    public void stop() {
        try {
            if (connector != null) {
                connector.close();
                connector = null;
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
    }
}
