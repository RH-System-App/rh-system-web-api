package com.rhsystem.api.rhsystemapi.domain.history.processor;

import com.rhsystem.api.rhsystemapi.domain.history.event.HistoryGenerator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
@Documented
public @interface HistoryProvider {

    String value();

    Class<? extends HistoryGenerator<?>> generator();

}
