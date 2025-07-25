package com.rhsystem.api.rhsystemapi.core.utils;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;

public class ReflectionUtils {

    public static void callMethod(Object object, String methodName, Object... args) {
        Class<?> type = object.getClass();
        Arrays.stream(type.getDeclaredMethods()).filter(m -> m.getName().equals(methodName)).forEach(m -> {
            try {
                m.setAccessible(true);
                m.invoke(object, args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static boolean hasAnnotation(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        return clazz.isAnnotationPresent(annotationClass);
    }

    public static <T extends Annotation> T getAnnotation(Class<?> clazz, Class<T> annotationClass) {
        return clazz.getAnnotation(annotationClass);
    }

    /**
     * Retorna todas as classes que estendem (ou implementam) `clazz`.
     */
    @SuppressWarnings("unchecked")
    public static <T> Set<Class<? extends T>> getSubClassOf(Class<T> clazz) {
        // Exemplo usando org.reflections:
        Reflections reflections = new Reflections("com.rhsystem.api.rhsystemapi");
        return reflections.getSubTypesOf(clazz);
    }

    public static <T> T newInstance(Class<T> clazz, Object... args) {
        try {
            return clazz.getConstructor(args.getClass()).newInstance(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
