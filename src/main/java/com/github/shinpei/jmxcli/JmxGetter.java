package com.github.shinpei.jmxcli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.*;

import java.util.concurrent.*;

import static com.github.shinpei.jmxcli.Printer.*;


public class JmxGetter implements CommandHandler {

    private static final Logger logger = LoggerFactory.getLogger(JmxGetter.class.getSimpleName());

    class GetFuture implements Callable<String> {
        JmxCliContext ctx;
        GetFuture(JmxCliContext ctx) {
            this.ctx = ctx;
        }

        public String call() throws Exception {
            MBeanServerConnection connector = CommandHandlerUtil.getMBeanServerConnection(ctx);
            Object obj = connector.getAttribute(ctx.objectName, ctx.attrName);
            logger.info("{}, {} = {}", ctx.objectName, ctx.attrName, obj.toString());

            return obj.toString();
        }
    }
    public void handle(JmxCliContext ctx) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<String > future = service.submit(new GetFuture(ctx));

        try {
            for (;;) {
                P("{}", future.get());
                logger.debug("sleeping {} milliseconds", ctx.refreshRate );
                Thread.currentThread().sleep(ctx.refreshRate);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
