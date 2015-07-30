package com.github.shinpei.jmxcli;

public class JmxCliContext {
    String port;
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        String port;
        public Builder() {

        }

        public Builder port(String port) {
            this.port = port;
            return this;
        }

        public JmxCliContext build() {
            JmxCliContext ctx = new JmxCliContext();
            ctx.port = port;
            return ctx;
        }

    }


}
