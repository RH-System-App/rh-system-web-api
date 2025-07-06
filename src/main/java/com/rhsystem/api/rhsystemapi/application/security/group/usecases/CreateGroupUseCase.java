package com.rhsystem.api.rhsystemapi.application.security.group.usecases;

import com.rhsystem.api.rhsystemapi.application.security.group.presenters.GroupCreatedPresenter;
import com.rhsystem.api.rhsystemapi.application.security.group.requests.CreateGroupRequest;
import com.rhsystem.api.rhsystemapi.core.exception.ValidationException;
import com.rhsystem.api.rhsystemapi.domain.security.functionality.Functionality;
import com.rhsystem.api.rhsystemapi.domain.security.functionality.FunctionalityRepository;
import com.rhsystem.api.rhsystemapi.domain.security.group.Group;
import com.rhsystem.api.rhsystemapi.domain.security.group.GroupRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Component
public class CreateGroupUseCase {

    private final GroupRepository groupRepository;
    private final FunctionalityRepository functionalityRepository;

    public CreateGroupUseCase(GroupRepository groupRepository, FunctionalityRepository functionalityRepository) {
        this.groupRepository = groupRepository;
        this.functionalityRepository = functionalityRepository;
    }

    @Transactional
    public GroupCreatedPresenter handle(CreateGroupRequest request) {
        var group = makeGroup(request);

        GroupCreatedPresenter presenter = new GroupCreatedPresenter();
        presenter.setGroupId(group.getKey().getValue());
        return presenter;
    }

    private Group makeGroup(CreateGroupRequest request) {
        var group = new Group();
        group.setName(request.getName());
        group.setFunctionalities(makeFunctionality(request.getFunctionalities()));
        group = groupRepository.save(group);

        return group;
    }

    public Collection<Functionality> makeFunctionality(Collection<String> functionalitiesCodes) {
        ValidationException e = new ValidationException();

        Collection<Functionality> functionalities = new ArrayList<>();
        for (int i = 0; i < functionalitiesCodes.size(); i++) {
            String code = IterableUtils.get(functionalitiesCodes, i);
            Optional<Functionality> functionality = functionalityRepository.findByCode(code);
            if (functionality.isPresent()) {
                functionalities.add(functionality.get());
            } else {
                e.addError("functionalities[" + i + "]", String.format("Functionality '%s' not found", code));
            }
        }

        if (e.hasErrors()) {
            throw e;
        }
        return functionalities;
    }

}
