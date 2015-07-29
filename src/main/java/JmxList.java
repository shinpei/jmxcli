package com.github.shinpei.jmxlist;

import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

//-Dcom.sun.management.jmxremote.port=8007 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false

public class JmxList {
    private static final Logger logger = LoggerFactory.getLogger(JmxList.class);
    static public void main(String[] args) {
        Options options = new Options();
        try {
            Map<String, Void> env = new HashMap<String, Void>();
            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://:8007/jmxrmi");
            JMXConnector connector = JMXConnectorFactory.connect(url, env);
            MBeanServerConnection connection = connector.getMBeanServerConnection();
            for (String name: connection.getDomains()) {
                logger.info("name : {} ", name);
            }
            MemoryMXBean memorybean = ManagementFactory.newPlatformMXBeanProxy(
                    connection,
                    ManagementFactory.MEMORY_MXBEAN_NAME,
                    MemoryMXBean.class
            );
            System.out.println("membean=" + memorybean);
            MemoryUsage usage = memorybean.getHeapMemoryUsage();
            System.out.print("usage: " + usage);
            connector.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
