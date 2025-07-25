package com.rhsystem.api.rhsystemapi.domain.history;

import com.rhsystem.api.rhsystemapi.domain.history.event.EntityPersistenceType;
import com.rhsystem.api.rhsystemapi.domain.user.User;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Utility class responsible for constructing {@link History} objects that capture
 * and describe changes or actions performed on domain entities. This class provides
 * functionality to compare current and previous states of an entity, identify the differences,
 * and generate a detailed {@link History} object that encapsulates these changes,
 * enabling proper traceability and audit logging.
 * <p>
 * The {@code HistoryBuilder} identifies changes by inspecting annotated fields using
 * {@link TrackChange}. It supports changes in primitive types, nested beans, and collections,
 * with each type of change appropriately categorized (e.g., creation, update, deletion).
 * The resulting {@link History} object includes metadata like user information,
 * creation moment, and a detailed set of change descriptions.
 * <p>
 * Changes are categorized according to the {@link EntityPersistenceType}, which dictates
 * whether the operation represents data creation, update, or deletion.
 * <p>
 * Thread safety: This class is thread-safe as it does not maintain any instance-level state
 * and operates on provided method parameters.
 */
public class HistoryBuilder {

    /**
     * Builds a History object by comparing two states of an entity (new and old) and capturing
     * the differences for audit purposes. The method analyzes the annotated fields in the entity
     * and generates a change log in the form of HistoryInfo instances, which are included
     * in the returned History object.
     *
     * @param opType the type of operation performed on the entity (CREATE, UPDATE, DELETE)
     * @param novo   the new state of the entity
     * @param antigo the old state of the entity
     * @param user   the user responsible for the operation
     * @return a History object representing the changes between the old and new states of the entity
     * @throws Exception if any reflection-related error occurs while accessing entity fields or methods
     */
    public static History build(
            EntityPersistenceType opType,
            Object novo,
            Object antigo,
            User user
    ) throws Exception {
        History history = new History();
        history.setUser(user);
        history.setMoment(LocalDateTime.now());

        Object sample = (novo != null ? novo : antigo);
        BeanInfo beanInfo = Introspector.getBeanInfo(sample.getClass(), Object.class);

        for (PropertyDescriptor prop : beanInfo.getPropertyDescriptors()) {
            Method getter = prop.getReadMethod();
            if (getter == null) continue;

            Field field;
            try {
                field = sample.getClass().getDeclaredField(prop.getName());
            } catch (NoSuchFieldException e) {
                continue;
            }
            TrackChange annot = field.getAnnotation(TrackChange.class);
            if (annot == null) continue;

            String label = annot.label().isEmpty() ? prop.getName() : annot.label();
            Object oldValObj = opType == EntityPersistenceType.CREATE
                    ? null
                    : getter.invoke(antigo);
            Object newValObj = getter.invoke(novo);

            HistoryInfo info;
            if (isSimple(field.getType())) {
                info = diffSimple(opType, label,
                        toDash(oldValObj),
                        toDash(newValObj));
            } else if (Collection.class.isAssignableFrom(field.getType())) {
                info = diffCollection(opType, label,
                        (Collection<?>) oldValObj,
                        (Collection<?>) newValObj);
            } else {
                info = diffNestedBean(opType, label,
                        newValObj, oldValObj, user);
            }

            if (info != null) {
                history.getInfo().add(info);
            }
        }

        return history;
    }

    /**
     * Converts the provided object to a string representation where null or empty values are replaced by a dash ("-").
     *
     * @param value the object to be converted; can be null or any object with a valid `toString()` method
     * @return the string representation of the object, or "-" if the object is null or its string value is empty
     */
    private static String toDash(Object value) {
        if (value == null) return "-";
        String s = value.toString().trim();
        return s.isEmpty() ? "-" : s;
    }

    /**
     * Determines whether a given class can be considered a simple type. A simple type
     * includes primitives, Strings, Numbers, Enums, Dates, and LocalDateTime.
     *
     * @param cls the class to evaluate
     * @return true if the class is a simple type, false otherwise
     */
    private static boolean isSimple(Class<?> cls) {
        return cls.isPrimitive()
                || cls == String.class
                || Number.class.isAssignableFrom(cls)
                || cls.isEnum()
                || Date.class.isAssignableFrom(cls)
                || cls == LocalDateTime.class;
    }

    /**
     * Compares two values associated with a specific entity property to identify changes,
     * and generates a history record if a meaningful change is found. The method
     * determines the type of change (create, update, delete) based on the operation type
     * and the old and new values, and returns a HistoryInfo object encapsulating this
     * change. If no change is detected, the method returns null.
     *
     * @param opType the type of operation being performed (CREATE, UPDATE, DELETE)
     * @param label  the name of the property being evaluated for changes
     * @param oldVal the previous value of the property
     * @param newVal the current value of the property
     * @return a HistoryInfo object detailing the property change, or null if no
     * significant change is detected
     */
    private static HistoryInfo diffSimple(
            EntityPersistenceType opType,
            String label,
            String oldVal,
            String newVal
    ) {
        HistoryType change = determineType(opType, oldVal, newVal);
        if (change == null) return null;
        return createInfo(label, oldVal, newVal, change);
    }

    /**
     * Generates a hierarchical representation of changes between the old and new states of a
     * nested bean object during a specified operation (CREATE or UPDATE). The method compares
     * the nested properties recursively, captures the differences, and constructs a HistoryInfo
     * instance encapsulating these changes. If no significant changes are identified, the method
     * returns null.
     *
     * @param opType the type of operation being performed (CREATE or UPDATE)
     * @param label  the label or name representing the nested bean being analyzed
     * @param novo   the new state of the nested bean (can be null for certain operations)
     * @param antigo the old state of the nested bean (can be null for certain operations)
     * @param user   the user responsible for the operation
     * @return a HistoryInfo object encapsulating the changes of the nested bean, or null
     * if no significant changes are detected
     * @throws Exception if any reflection-related error occurs during the comparison process
     */
    private static HistoryInfo diffNestedBean(
            EntityPersistenceType opType,
            String label,
            Object novo,
            Object antigo,
            User user
    ) throws Exception {
        if (opType == EntityPersistenceType.CREATE && novo == null) return null;
        if (opType == EntityPersistenceType.UPDATE && (novo == null || antigo == null)) return null;

        // chama recursivamente para gerar os filhos
        History sub = build(opType, novo, antigo, user);
        if (sub.getInfo().isEmpty()) return null;

        HistoryInfo info = new HistoryInfo();
        info.setProperty(label);
        // garante "-" nos próprios valores
        info.setOldValue("-");
        info.setValue("-");
        // marca o tipo como CREATE ou UPDATE
        info.setType(opType == EntityPersistenceType.CREATE
                ? HistoryType.CREATE
                : HistoryType.UPDATE);
        info.getChildren().addAll(sub.getInfo());
        return info;
    }

    /**
     * Compares two collections (old and new) to identify differences in their contents,
     * such as additions or deletions, and generates a HistoryInfo object to log these changes.
     * The method determines the type of operation (create or update) and captures
     * the details of created and deleted items in the collections.
     *
     * @param opType the type of operation being performed (CREATE or UPDATE)
     * @param label  the label or name representing the collection being evaluated
     * @param oldCol the original collection before the operation; can be null
     * @param newCol the updated collection after the operation; can be null
     * @return a HistoryInfo object representing the differences between the old and new
     * collections, or null if no significant changes are detected
     */
    private static HistoryInfo diffCollection(
            EntityPersistenceType opType,
            String label,
            Collection<?> oldCol,
            Collection<?> newCol
    ) {
        Collection<?> oldC = Optional.ofNullable(oldCol).orElse(Collections.emptyList());
        Collection<?> newC = Optional.ofNullable(newCol).orElse(Collections.emptyList());

        // se não houve criação nem deleção, ignora
        boolean anyChange =
                !newC.stream().allMatch(oldC::contains) ||
                        !oldC.stream().allMatch(newC::contains);
        if (!anyChange) {
            return null;
        }

        HistoryInfo info = new HistoryInfo();
        info.setProperty(label);
        // também sempre "-" no nó pai
        info.setOldValue("-");
        info.setValue("-");
        info.setType(opType == EntityPersistenceType.CREATE
                ? HistoryType.CREATE
                : HistoryType.UPDATE);

        // itens criados
        for (Object item : newC) {
            if (!oldC.contains(item)) {
                info.getChildren().add(
                        createInfo(item.toString(), "-", item.toString(), HistoryType.CREATE)
                );
            }
        }
        // itens deletados
        for (Object item : oldC) {
            if (!newC.contains(item)) {
                info.getChildren().add(
                        createInfo(item.toString(), item.toString(), "-", HistoryType.DELETE)
                );
            }
        }
        return info;
    }

    /**
     * Determines the type of change performed on an entity based on the operation type and
     * the comparison of old and new values. This method evaluates the operation type and
     * the transition between old and new values to deduce whether the entity was created,
     * updated, or deleted. If no significant change is detected, the method returns null.
     *
     * @param opType the type of operation performed on the entity, such as CREATE, UPDATE, or DELETE
     * @param oldVal the old value of the entity or property before the operation
     * @param newVal the new value of the entity or property after the operation
     * @return the type of change as a {@code HistoryType} (CREATE, UPDATE, DELETE), or null if no change is detected
     */
    private static HistoryType determineType(
            EntityPersistenceType opType,
            String oldVal,
            String newVal
    ) {
        if (opType == EntityPersistenceType.CREATE) {
            return HistoryType.CREATE;
        }
        if (Objects.equals(oldVal, newVal)) {
            return null; // sem mudança
        }
        if ("-".equals(newVal)) {
            return HistoryType.DELETE;
        }
        if ("-".equals(oldVal)) {
            return HistoryType.CREATE;
        }
        return HistoryType.UPDATE;
    }

    /**
     * Creates and initializes a new instance of {@code HistoryInfo} with the specified
     * property name, old value, new value, and change type.
     *
     * @param prop the name of the property being logged
     * @param oldV the old value of the property before the change
     * @param newV the new value of the property after the change
     * @param type the type of change (e.g., CREATE, UPDATE, DELETE) represented by {@code HistoryType}
     * @return an instance of {@code HistoryInfo} populated with the provided details
     */
    private static HistoryInfo createInfo(
            String prop,
            String oldV,
            String newV,
            HistoryType type
    ) {
        HistoryInfo hi = new HistoryInfo();
        hi.setProperty(prop);
        hi.setOldValue(oldV);
        hi.setValue(newV);
        hi.setType(type);
        return hi;
    }
}
