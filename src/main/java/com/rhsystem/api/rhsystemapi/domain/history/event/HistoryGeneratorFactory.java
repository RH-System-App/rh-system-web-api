package com.rhsystem.api.rhsystemapi.domain.history.event;

import com.rhsystem.api.rhsystemapi.core.utils.ReflectionUtils;
import com.rhsystem.api.rhsystemapi.core.valueobject.DomainEntity;
import com.rhsystem.api.rhsystemapi.domain.history.processor.HistoryProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class HistoryGeneratorFactory {

    // Mapeia o "nome" (da annotation) para a função que cria o HistoryGenerator
    private static final Map<String, Function<DomainEntity<?>, HistoryGenerator<?>>> generators = new HashMap<>();

    static {
        for (Class<? extends DomainEntity<?>> entityClass
                : ReflectionUtils.getSubClassOf(DomainEntity.class)) {

            if (ReflectionUtils.hasAnnotation(entityClass, HistoryProvider.class)) {
                HistoryProvider ann = ReflectionUtils.getAnnotation(entityClass, HistoryProvider.class);
                String name = ann.value();

                // Supondo que sua annotation também aponte para a classe de HistoryGenerator:
                @SuppressWarnings("unchecked")
                Class<? extends HistoryGenerator<?>> generatorClass = ann.generator();

                // Função que, a partir de qualquer Entity, cria o Generator correto
                Function<DomainEntity<?>, HistoryGenerator<?>> func = entity ->
                        ReflectionUtils.newInstance(generatorClass, entity);

                generators.put(name, func);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends DomainEntity<?>> HistoryGenerator<T> getGenerator(T entity) {
        String key = ReflectionUtils.getAnnotation(entity.getClass(), HistoryProvider.class).value();
        Function<DomainEntity<?>, HistoryGenerator<?>> func = generators.get(key);

        if (func == null) {
            throw new IllegalArgumentException("Nenhum HistoryGenerator registrado para: " + key);
        }
        return (HistoryGenerator<T>) func.apply(entity);
    }
}
