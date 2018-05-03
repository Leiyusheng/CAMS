package com.lei.main.comm.listener;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 * 初始系统资源用户、字典、调度器
 * 
 */
public class AppSystemListener implements ServletContextListener {

    private Logger log = Logger.getLogger(AppSystemListener.class);

    private ServletContext context = null;
    

    private <T> T getBean(ApplicationContext context, Class<T> cls) {
        return (T) BeanFactoryUtils.beanOfType(context, cls);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        this.context = event.getServletContext();
        // SPRINT CONTENT
        ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.context);
        loadDictionary(context);
    }

    public void loadDictionary(ApplicationContext context){


    }
}