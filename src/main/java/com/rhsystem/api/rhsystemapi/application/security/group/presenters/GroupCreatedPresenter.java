package com.rhsystem.api.rhsystemapi.application.security.group.presenters;

import java.util.UUID;

public class GroupCreatedPresenter {

    private UUID groupId;

    public UUID getGroupId() {
        return groupId;
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }
}
