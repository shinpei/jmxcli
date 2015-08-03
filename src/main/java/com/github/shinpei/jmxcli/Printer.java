package com.github.shinpei.jmxcli;

import com.google.common.base.CharMatcher;

import java.io.PrintWriter;

public class Printer {
    static void P(String fmt, int arg) {
        fmt = fmt.replace("{}", "%d");
        System.out.printf(fmt + "\n", arg);
    }
    static void P(String fmt, String arg) {
        fmt = fmt.replace("{}", "%s");
        System.out.printf(fmt + "\n", arg);
    }

    static void P(String fmt, Object arg) {
        fmt = fmt.replace("{}", "%s");
        System.out.printf(fmt + "\n", arg);
    }

    static void P (String fmt, Object... args) {
        PrintWriter pw = new PrintWriter(System.out);
        pw.printf(fmt, args);
        pw.flush();
    }
}
