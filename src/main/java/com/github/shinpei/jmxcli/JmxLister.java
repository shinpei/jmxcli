package com.github.shinpei.jmxcli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class JmxLister implements CommandHandler {

    private static final Logger logger = LoggerFactory.getLogger(JmxLister.class.getSimpleName());
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

    public void handle(JmxCliContext context) {

        try {
            MBeanServerConnection connection = CommandHandlerUtil.getMBeanServerConnection(context);
            List<String> domains = new ArrayList<String >();
            for (String name: connection.getDomains()) {
                logger.info("{} ", name);
                domains.add(name);
                queryWithDomain(connection, name);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
