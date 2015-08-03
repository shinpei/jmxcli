package com.github.shinpei.jmxcli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class CommandLineOptions {

    static public Options getCommandLineOptions () {
        Options options = new Options();
        Option domainOption = Option.builder("domain").argName("domain name")
                .hasArg()
                .desc("use given domain to list")
                .build();
        Option portOption = Option.builder("port").argName("port number")
                .hasArg()
                .desc("use given port to connect remote mbean server")
                .build();
        Option onOption = Option.builder("objectname").argName("object name")
                .hasArg()
                .desc("use given object name for fetching value")
                .build();

        Option attrOption = Option.builder("attribute").argName("attribute name")
                .hasArg()
                .desc("use given attribute name for fetching value")
                .build();
        Option refreshRateOption = Option.builder("s").argName("refresh rate")
                .hasArg()
                .desc("use given milliseconds for refreshing value")
                .build();



        Option helpOption = new Option("help", "print this message");
        Option versionOption = new Option("version", "print version");

        options.addOption(helpOption);
        options.addOption(domainOption);
        options.addOption(portOption);
        options.addOption(versionOption);
        options.addOption(onOption);
        options.addOption(attrOption);
        options.addOption(refreshRateOption);

        return  options;
    }
}
