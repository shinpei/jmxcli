package com.github.shinpei.jmxcli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.*;
import java.io.IOException;
import static com.github.shinpei.jmxcli.Printer.*;


public class JmxGetter implements CommandHandler {

    private static final Logger logger = LoggerFactory.getLogger(JmxGetter.class.getSimpleName());
    public void handle(JmxCliContext ctx) {
        try {
            MBeanServerConnection connector = CommandHandlerUtil.getMBeanServerConnection(ctx);
            Object obj = connector.getAttribute(ctx.objectName, ctx.attrName);
            logger.info("{}, {} = {}", ctx.objectName, ctx.attrName, obj.toString());
            P("{}", obj.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AttributeNotFoundException e) {
            e.printStackTrace();
        } catch (MBeanException e) {
            e.printStackTrace();
        } catch (ReflectionException e) {
            e.printStackTrace();
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
        }
    }
}
