package com.github.shinpei.jmxcli;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

public class JmxCliContext {
    String port;
    ObjectName objectName;
    String attrName;
    int refreshRate;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        String port;
        ObjectName objectName;
        String attrName;
        int refreshRate;

        public Builder() {

        }

        public Builder port(String port) {
            this.port = port;
            return this;
        }

        public Builder objectName(String objectName) throws MalformedObjectNameException {
            this.objectName = new ObjectName(objectName);
            return this;
        }
        public Builder attrName(String attrName) {
            this.attrName = attrName;
            return this;
        }
        public Builder refreshRate(String refreshRate) {
            this.refreshRate = Integer.parseInt(refreshRate);
            return this;

        }

        public JmxCliContext build() {
            JmxCliContext ctx = new JmxCliContext();
            ctx.port = port;
            ctx.objectName = objectName;
            ctx.attrName = attrName;
            ctx.refreshRate = refreshRate;
            return ctx;
        }

    }


}
