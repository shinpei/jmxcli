package com.github.shinpei.jmxcli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.*;
import java.io.IOException;
import static com.github.shinpei.jmxcli.Printer.*;
public class JmxListAttr implements CommandHandler {

    private static final Logger logger = LoggerFactory.getLogger(JmxListAttr.class.getSimpleName());
    public void handle(JmxCliContext ctx) {
        try {
            MBeanServerConnection connecter = CommandHandlerUtil.getMBeanServerConnection(ctx);
            MBeanInfo info  = connecter.getMBeanInfo(ctx.objectName);
            MBeanAttributeInfo[] attrs = info.getAttributes();
            P("Attributes for {}", ctx.objectName);
            for (MBeanAttributeInfo attr: attrs) {
                P("+ {}", attr.getName());
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (ReflectionException e) {
            e.printStackTrace();
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
