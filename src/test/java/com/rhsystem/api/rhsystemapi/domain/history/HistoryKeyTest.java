package com.rhsystem.api.rhsystemapi.domain.history;

import org.junit.jupiter.api.Test;

import java.util.UUID;

class HistoryKeyTest {

    @Test
    void testToString() {
        HistoryKey historyKey = HistoryKey.of("user", UUID.randomUUID().toString());

        System.out.println(historyKey);
    }
}