package com.github.shinpei.jmxcli;

import org.apache.commons.cli.*;
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

import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//-Dcom.sun.management.jmxremote.port=8007 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false

public class JmxCli {
    private static final Logger logger = LoggerFactory.getLogger(JmxCli.class.getSimpleName());
    private static final String DEFAULT_MBEAN_SERVER_PORT = "3000";
    static public void main(String[] args) {
        Options options = new Options();
        Option domainOption = Option.builder("domain").argName("domain name")
                .hasArg()
                .desc("use given domain to list")
                .build();
        Option portOption = Option.builder("port").argName("port number")
                .hasArg()
                .desc("use given port to connect remote mbean server")
                .build();

        Option helpOption = new Option("help", "print this message");
        Option versionOption = new Option("version", "print version");

        options.addOption(helpOption);
        options.addOption(domainOption);
        options.addOption(portOption);
        options.addOption(versionOption);
        String port = DEFAULT_MBEAN_SERVER_PORT;
        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine line = parser.parse(options, args);
            if (line.hasOption("help")) {
                // help
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("jmxlist", options);
                return;
            }
            if (line.hasOption("version")) {
                // version
                PrintWriter pw = new PrintWriter(System.out);
                pw.print("version ");
                pw.println(JmxCliConfig.getVersion());
                pw.flush();
                return;
            }
            if (line.hasOption("domain")) {
                String domain = line.getOptionValue("domain");
                logger.info("Domain = {} ", domain);
            }
            if (line.hasOption("port")) {
                port = line.getOptionValue("port");
            }
        } catch (ParseException e) {
            logger.error("Couldn't parse command line");
        }


        try {
            Map<String, Void> env = new HashMap<String, Void>();
            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://:"  + port + "/jmxrmi");
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
