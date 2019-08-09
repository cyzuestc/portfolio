package cyz.ink.portfolio.listerner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @ Author      : Zink
 * @ Date        : Created in 13:50 2019/8/9
 * @ Description :
 * @ Version     : 1.0
 **/

@WebListener
public class SessionListener implements HttpSessionListener,HttpSessionAttributeListener{
    public static final Logger logger = LoggerFactory.getLogger(SessionListener.class);
    public static int onlineNumber = 0;

    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
        logger.info("attributeAdded");
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent se) {
        logger.info("attributeRemoved");
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent se) {
        logger.info("attributeReplaced");
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        logger.info("sessionCreated");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.info("sessionDestroyed");
    }
}
