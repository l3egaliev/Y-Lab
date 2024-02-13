package kg.rakhim.classes.servlet;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import kg.rakhim.classes.context.ApplicationContext;
import kg.rakhim.classes.context.UserContext;

@WebListener
public class ApplicationContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext.loadContext();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        UserContext.clear();
    }
}
