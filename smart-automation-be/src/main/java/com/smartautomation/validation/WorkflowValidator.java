package com.smartautomation.validation;

import com.smartautomation.model.WorkflowDefinition;

public interface WorkflowValidator {
    
    ValidationResult validate(WorkflowDefinition definition);
}
