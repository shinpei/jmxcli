package com.github.shinpei.jmxcli;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MalformedObjectNameException;
import java.io.PrintWriter;
import java.util.Map;

import static com.github.shinpei.jmxcli.Printer.*;

//-Dcom.sun.management.jmxremote.port=8007 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false


public class JmxCli {
    private static final Logger logger = LoggerFactory.getLogger(JmxCli.class.getSimpleName());
    private static final String DEFAULT_MBEAN_SERVER_PORT = "3000";
    static final Map<String, CommandHandler> handlers = ImmutableMap.<String, CommandHandler>builder()
            .put("ls", new JmxLister())
            .put("attr", new JmxListAttr())
            .put("get", new JmxGetter())
            .put("mget", new JmxMultiGetter())
            .build();

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
                P("version {}", JmxCliConfig.getVersion());
                return;
            }
            JmxCliContext.Builder builder = JmxCliContext.builder();
            if (line.hasOption("domain")) {
                String domain = line.getOptionValue("domain");
                logger.debug("Domain = {} ", domain);
            }
            if (line.hasOption("port")) {
                port = line.getOptionValue("port");
            }
            if (line.hasOption("objectname")) {
                try {
                    builder.objectName(line.getOptionValue("objectname"));
                } catch (MalformedObjectNameException e) {
                    e.printStackTrace();
                }
            }
            if (line.hasOption("attribute")) {
                builder.attrName(line.getOptionValue("attribute"));
            }


            // create context
            JmxCliContext ctx = builder.port(port).build();
            // parse subcommand
            logger.debug("args {}", line.getArgList());
            for (String command : line.getArgList()) {

                CommandHandler hdlr = handlers.get(command);
                if (hdlr != null) {
                    hdlr.handle(ctx);
                    // should be a single command
                    break;
                } else {
                    logger.error("Cannot found handler for command name '{}'", command);
                }
            }

        } catch (ParseException e) {
            logger.error("Couldn't parse command line {}", e);
        }

    }

}
