package com.rhsystem.api.rhsystemapi.domain.history;

public interface HistoryRepository {

    History save(History history);

    History findByEntityAndEntityKey(String entity, String entityKey);

}
