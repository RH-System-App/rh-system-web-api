package com.rhsystem.api.rhsystemapi.core.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Objects {

    public static Collection<Class<?>> PRIMITIVES;
    public static Collection<Class<?>> COLLECTIONS_TYPES;


    static {
        PRIMITIVES = new ArrayList<Class<?>>();
        PRIMITIVES.add(boolean.class);
        PRIMITIVES.add(byte.class);
        PRIMITIVES.add(short.class);
        PRIMITIVES.add(int.class);
        PRIMITIVES.add(long.class);
        PRIMITIVES.add(float.class);
        PRIMITIVES.add(double.class);

        COLLECTIONS_TYPES = new ArrayList<>();
        COLLECTIONS_TYPES.add(ArrayList.class);
        COLLECTIONS_TYPES.add(List.class);
        COLLECTIONS_TYPES.add(Collection.class);
        COLLECTIONS_TYPES.add(Iterable.class);
        COLLECTIONS_TYPES.add(Object[].class);
        COLLECTIONS_TYPES.add(String[].class);
        COLLECTIONS_TYPES.add(int[].class);
        COLLECTIONS_TYPES.add(long[].class);
        COLLECTIONS_TYPES.add(float[].class);
        COLLECTIONS_TYPES.add(double[].class);
        COLLECTIONS_TYPES.add(short[].class);
        COLLECTIONS_TYPES.add(byte[].class);
        COLLECTIONS_TYPES.add(char[].class);
        COLLECTIONS_TYPES.add(boolean[].class);

    }

    public static Object getValue(String fieldName, Object obj) {
        try {
            Field f = extractField(fieldName, obj);
            if (f != null) {
                f.setAccessible(true);
                return f.get(obj);
            }

        } catch (Exception ignored) {
        }
        return null;
    }

    private static Field extractField(String fieldName, Object obj) {
        try {
            Class<?> clazz = obj.getClass();
            return clazz.getDeclaredField(fieldName);
        } catch (Exception e) {
            return null;
        }

    }

    public static boolean isObject(String fieldName, Object obj) {
        Field f = extractField(fieldName, obj);
        if (f != null) {
            Class<?> type = f.getType();
            return !(type.isPrimitive() || PRIMITIVES.contains(type));
        }
        return false;
    }

    public static boolean isPrimitive(String fieldName, Object obj) {
        Field f = extractField(fieldName, obj);
        if (f != null) {
            Class<?> type = f.getType();
            return type.isPrimitive() || PRIMITIVES.contains(type);
        }
        return false;
    }

    public static boolean isCollection(String fieldName, Object obj) {
        Field f = extractField(fieldName, obj);
        if (f != null) {
            Class<?> type = f.getType();
            return COLLECTIONS_TYPES.contains(type);
        }
        return false;
    }
}
