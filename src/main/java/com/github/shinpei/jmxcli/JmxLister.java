package com.github.shinpei.jmxcli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JmxLister {

    private static final Logger logger = LoggerFactory.getLogger(JmxLister.class.getSimpleName());
    private JmxCliContext context;

    public JmxLister (JmxCliContext context) {
        this.context = context;
    }

    public void list() {

        try {
            Map<String, Void> env = new HashMap<String, Void>();
            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://:"  + context.port + "/jmxrmi");
            JMXConnector connector = JMXConnectorFactory.connect(url, env);
            MBeanServerConnection connection = connector.getMBeanServerConnection();
            List<String> domains = new ArrayList<String >();
            for (String name: connection.getDomains()) {
                logger.info("{} ", name);
                domains.add(name);
                queryWithDomain(connection, name);
            }
            connector.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void queryWithDomain(MBeanServerConnection connection, String domain){

        try {
            ObjectName name = new ObjectName(domain + ":*");
            for (Object obj : connection.queryMBeans(name, null)) {
                ObjectInstance objectInstance = (ObjectInstance)obj;
                logger.info(" + {}", objectInstance.getObjectName());
                //logger.info("Class : {}", objectInstance.getClassName() );
            }
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
