package com.rhsystem.api.rhsystemapi.infrastructure.persistence.history;

import com.rhsystem.api.rhsystemapi.domain.history.History;
import com.rhsystem.api.rhsystemapi.domain.history.HistoryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class HistoryJpaRepository implements HistoryRepository {


    @Override
    public History save(History history) {
        return null;
    }
}
