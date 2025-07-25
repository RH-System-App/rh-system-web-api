package com.rhsystem.api.rhsystemapi.domain.history;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TrackChange {
    /**
     * Rótulo legível; se vazio, usa o nome do campo
     */
    String label() default "";
}