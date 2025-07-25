package com.rhsystem.api.rhsystemapi.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * A utility class that provides access to the Spring application context, allowing retrieval
 * of Spring-managed beans. This class implements {@link ApplicationContextAware} to receive
 * the {@link ApplicationContext} and store it statically for use in the application.
 * <p>
 * This class provides static methods to fetch beans by their type or name.
 */
@Component
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext context;

    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    public static Object getBean(String name) {
        return context.getBean(name);
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        context = ctx;
    }
}
