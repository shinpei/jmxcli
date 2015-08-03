package com.github.shinpei.jmxcli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.*;

import java.io.IOException;
import java.util.concurrent.*;

import static com.github.shinpei.jmxcli.Printer.*;


public class JmxGetter implements CommandHandler {

    private static final Logger logger = LoggerFactory.getLogger(JmxGetter.class.getSimpleName());


    public void handle(JmxCliContext ctx) {
        try {
            for (;;) {
                MBeanServerConnection connector = CommandHandlerUtil.getMBeanServerConnection(ctx);
                Object obj = connector.getAttribute(ctx.objectName, ctx.attrName);
                CommandHandlerUtil.closeConnection();
                P("{}", obj.toString());
                Thread.currentThread().sleep(ctx.refreshRate);
            }
        } catch (AttributeNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ReflectionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
        } catch (MBeanException e) {
            e.printStackTrace();
        }
    }
}
