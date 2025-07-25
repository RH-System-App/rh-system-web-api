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

    /**
     * A static reference to the Spring {@link ApplicationContext}, allowing access to Spring-managed
     * beans throughout the application. This variable is set when the application context is injected
     * via the {@code setApplicationContext} method in the {@link SpringContext} class.
     * <p>
     * Note: This variable should be accessed through the static utility methods
     * {@link SpringContext#getBean(Class)} or {@link SpringContext#getBean(String)},
     * rather than being accessed directly.
     */
    private static ApplicationContext context;

    /**
     * Retrieves a Spring-managed bean of the specified type from the application context.
     *
     * @param <T>   the type of the bean to retrieve
     * @param clazz the class object representing the type of the bean to retrieve
     * @return the Spring-managed bean instance of the specified type
     */
    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    /**
     * Retrieves a Spring-managed bean by its name from the application context.
     *
     * @param name the name of the bean to retrieve
     * @return the Spring-managed bean instance associated with the specified name
     */
    public static Object getBean(String name) {
        return context.getBean(name);
    }

    /**
     * Sets the current Spring {@link ApplicationContext} to a static variable for global access.
     * This method is invoked by the Spring framework at runtime to inject the application context into the class.
     *
     * @param ctx the Spring {@link ApplicationContext} to set
     * @throws BeansException if there is an issue setting the application context
     */
    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        context = ctx;
    }
}
