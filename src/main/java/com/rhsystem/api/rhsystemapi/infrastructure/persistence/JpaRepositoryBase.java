package com.rhsystem.api.rhsystemapi.infrastructure.persistence;

import com.rhsystem.api.rhsystemapi.core.ObjectMapper;
import com.rhsystem.api.rhsystemapi.core.persistence.Repository;
import com.rhsystem.api.rhsystemapi.core.valueobject.DomainEntity;
import com.rhsystem.api.rhsystemapi.domain.history.event.DomainPersistedEvent;
import com.rhsystem.api.rhsystemapi.domain.history.event.EntityPersistenceType;
import com.rhsystem.api.rhsystemapi.domain.history.event.HistoryGenerator;
import com.rhsystem.api.rhsystemapi.domain.history.event.HistoryGeneratorFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public abstract class JpaRepositoryBase<
        D extends DomainEntity<ID>,
        ID,
        P,
        PK
        > implements Repository<D, ID> {

    protected final ObjectMapper<D, P> mapper;
    private final ApplicationEventPublisher publisher;

    protected JpaRepositoryBase(
            ObjectMapper<D, P> mapper,
            ApplicationEventPublisher publisher
    ) {
        this.mapper = mapper;
        this.publisher = publisher;
    }

    private static <T extends DomainEntity<K>, K> HistoryGenerator<T> getGenerator(T domain) {
        return HistoryGeneratorFactory.getGenerator(domain);
    }

    protected abstract JpaRepository<P, PK> getJpaRepository();

    protected abstract PK extractKey(ID id);

    protected P toPersistence(D domain) {
        return mapper.toEntity(domain);
    }

    protected D toDomain(P persistence) {
        return mapper.toDomain(persistence);
    }


    @Override
    @Transactional
    public D save(D domain) {
        boolean novo = domain.getKey() == null;

        P persisted = getJpaRepository().save(toPersistence(domain));

        D saved = toDomain(persisted);

        publisher.publishEvent(
                new DomainPersistedEvent<>(saved,
                        novo ? EntityPersistenceType.CREATE : EntityPersistenceType.UPDATE
                )
        );

        return saved;
    }

    @Override
    public Optional<D> findById(ID id) {
        return getJpaRepository()
                .findById(extractKey(id))
                .map(this::toDomain);
    }

    @Override
    @Transactional
    public void delete(D domain) {
        getJpaRepository().delete(toPersistence(domain));
        publisher.publishEvent(
                new DomainPersistedEvent<>(domain, EntityPersistenceType.DELETE)
        );
    }
}
