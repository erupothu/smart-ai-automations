package com.smartautomation.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ValidationResult {
    
    private final List<ValidatonError> errors;

    public ValidationResult() {
        this.errors = new ArrayList<>();
    }

    public ValidationResult(List<ValidatonError> errors) {
        this.errors = new ArrayList<>(errors);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public List<ValidatonError> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    public void addError(ValidatonError error) {
        this.errors.add(error);
    }

    public void addErrors(List<ValidatonError> errors) {
        this.errors.addAll(errors);
    }

    
}
