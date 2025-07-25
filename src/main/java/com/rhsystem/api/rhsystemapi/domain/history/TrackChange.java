package com.rhsystem.api.rhsystemapi.domain.history;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for tracking changes made to specific fields within a class.
 * The @TrackChange annotation is used to indicate that a particular field
 * should be monitored for changes, typically for the purposes of auditing
 * or maintaining a record of modifications.
 * <p>
 * This annotation can be applied to fields, and it accepts an optional
 * label parameter to specify a user-friendly name for the field.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TrackChange {

    /**
     * Defines an optional label for the annotated field.
     * The label is intended to provide a more descriptive, user-friendly name
     * for the field being tracked.
     *
     * @return the optional label for the field; returns an empty string by default
     */
    String label() default "";
}