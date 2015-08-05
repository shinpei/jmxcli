package com.github.shinpei.jmxcli;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JmxCliCommandLineParser {
    private static final Logger logger = LoggerFactory.getLogger(JmxCliCommandLineParser.class.getSimpleName());

    public CommandLine parse(JmxCliOptions options, String[] arguments) throws ParseException {
        return parse(options, arguments, false);
    }

    public CommandLine parse(JmxCliOptions options, String[] arguments, boolean stopAtNonOption) throws ParseException {
        for (String argument: arguments)  {
            handle(argument);
        }
        return null;
    }

    private void handle (String token) throws ParseException
    {
        if (token.startsWith("--")) {
            handleLongOption(token);
        }
        else if (token.startsWith("-") && !"-".equals(token)) {
            handleShortoption(token);
        }
        else{
            handleUnknownToken


    }



    }
}
