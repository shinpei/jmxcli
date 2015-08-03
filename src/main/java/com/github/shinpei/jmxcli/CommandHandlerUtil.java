package com.github.shinpei.jmxcli;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommandHandlerUtil {

    static JMXConnector connector = null;
    static public MBeanServerConnection getMBeanServerConnection(JmxCliContext context) throws IOException {
        Map<String, Void> env = new HashMap<String, Void>();

        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://:" + context.port + "/jmxrmi");
        if (connector == null) {
            connector = JMXConnectorFactory.connect(url, env);
        }
        // We should close connector
        return connector.getMBeanServerConnection();
    }

}
