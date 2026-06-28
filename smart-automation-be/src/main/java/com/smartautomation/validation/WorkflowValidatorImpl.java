package com.smartautomation.validation;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.smartautomation.model.WorkflowDefinition;

@Service
public class WorkflowValidatorImpl implements WorkflowValidator {

    private static final Set<String> VALID_CONNECTOR_TYPES = Set.of(
        "SCHEDULE", "GMAIL", "MYSQL", "MONGODB", "AGENTIC_AI_CREW"
    );

    

    @Override
    public ValidationResult validate(WorkflowDefinition definition) {
        throw new UnsupportedOperationException("Unimplemented method 'validate'");
    }
    
}
