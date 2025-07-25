package com.rhsystem.api.rhsystemapi.domain.history.event;

import com.rhsystem.api.rhsystemapi.core.utils.ReflectionUtils;
import com.rhsystem.api.rhsystemapi.core.valueobject.DomainEntity;
import com.rhsystem.api.rhsystemapi.domain.history.processor.HistoryProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class HistoryGeneratorFactory {

    private static final Map<String, Function<DomainEntity<?>, HistoryGenerator<?>>> generators = new HashMap<>();

    static {
        // 1) receba como Class<?> cru
        Set<Class<? extends DomainEntity>> rawClasses = ReflectionUtils.getSubClassOf(DomainEntity.class);

        for (Class<?> raw : rawClasses) {
            if (!raw.isAnnotationPresent(HistoryProvider.class)) {
                continue;
            }

            // 2) faça o cast para o parametrizado
            @SuppressWarnings("unchecked")
            Class<? extends DomainEntity<?>> entityClass;
            entityClass = (Class<? extends DomainEntity<?>>) raw;

            HistoryProvider prov = entityClass.getAnnotation(HistoryProvider.class);
            String key = prov.value();
            Class<? extends HistoryGenerator<?>> genClass = prov.generator();

            // 3) registre a função
            generators.put(key, domain ->
                    ReflectionUtils.newInstance(genClass, domain)
            );
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends DomainEntity<?>> HistoryGenerator<T> getGenerator(T entity) {
        HistoryProvider prov = entity.getClass().getAnnotation(HistoryProvider.class);
        if (prov == null) {
            throw new IllegalArgumentException(
                    "@" + HistoryProvider.class.getSimpleName() +
                            " ausente em " + entity.getClass().getName());
        }

        Function<DomainEntity<?>, HistoryGenerator<?>> func = generators.get(prov.value());
        if (func == null) {
            throw new IllegalStateException("Nenhum generator para key=" + prov.value());
        }

        return (HistoryGenerator<T>) func.apply(entity);
    }
}
