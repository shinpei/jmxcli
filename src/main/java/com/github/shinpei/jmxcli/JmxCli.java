package com.github.shinpei.jmxcli;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.PrintWriter;

//-Dcom.sun.management.jmxremote.port=8007 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false

public class JmxCli {
    private static final Logger logger = LoggerFactory.getLogger(JmxCli.class.getSimpleName());
    private static final String DEFAULT_MBEAN_SERVER_PORT = "3000";
    static public void main(String[] args) {

        Options options = CommandLineOptions.getCommandLineOptions();
        String port = DEFAULT_MBEAN_SERVER_PORT;
        try {
            CommandLineParser parser = new JmxCliCommandLineParser();
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

            // create context
            JmxCliContext ctx = JmxCliContext.builder().port(port).build();
            // parse subcommand
            logger.info("args {}", line.getArgList());
            for (String command : line.getArgList()) {
                if ("list".equals(command)) {
                    JmxLister lister = new JmxLister(ctx);
                    lister.list();
                }

            }

        } catch (ParseException e) {
            logger.error("Couldn't parse command line");
        }

    }

}
