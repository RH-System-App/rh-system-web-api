package com.rhsystem.api.rhsystemapi.core.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;


class NextedClass {
    private String name;
}

class ObjectTest {
    private String name;
    private int age;
    private Collection<NextedClass> nextedClasses;

    private NextedClass nextedClass = new NextedClass();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NextedClass getNextedClass() {
        return nextedClass;
    }

    public void setNextedClass(NextedClass nextedClass) {
        this.nextedClass = nextedClass;
    }
}


class ObjectsTest {

    private final Logger logger = Logger.getLogger(ObjectsTest.class.getName());
    private ObjectTest objectsTest;

    @BeforeEach
    public void beforeEach() {
        objectsTest = new ObjectTest();
    }

    @Test
    void getValue() {
        objectsTest.setName("John Due");

        Object value = Objects.getValue("name", objectsTest);
        logger.info(String.valueOf(value));

        assertEquals("John Due", value);
    }

    @Test
    void isObjectWithNextedClass() {
        Object value = Objects.isObject("nextedClass", objectsTest);
        assertEquals(true, value);
    }


    @Test
    void isObjectWithString() {
        Object value = Objects.isObject("name", objectsTest);
        assertEquals(true, value);
    }

    @Test
    void isObjectWithNull() {
        Object value = Objects.isObject("name", null);
        assertEquals(false, value);
    }

    @Test
    void isObjectWIthNumber() {
        Object value = Objects.isObject("name", 1);
        assertEquals(false, value);
    }

    @Test
    void isPrimitiveWithNull() {
        Object value = Objects.isPrimitive("name", null);
        assertEquals(false, value);
    }

    @Test
    void isPrimitiveWithNumber() {
        Object value = Objects.isPrimitive("age", objectsTest);
        assertEquals(true, value);
    }

    @Test
    void isCollection() {
        Object value = Objects.isCollection("nextedClasses", objectsTest);
        assertEquals(true, value);
    }
}