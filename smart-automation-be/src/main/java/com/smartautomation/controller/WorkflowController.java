package com.smartautomation.controller;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartautomation.model.WorkflowDefinition;
import com.smartautomation.service.PersistenceService;
import com.smartautomation.service.SchedularService;
import com.smartautomation.service.WorkflowExecutionService;
import com.smartautomation.validation.ValidationResult;
import com.smartautomation.validation.WorkflowValidator;

@RestController
@RequestMapping("/api/workflows")
public class WorkflowController {
    
    private final PersistenceService persistenceService;
    private final WorkflowValidator workflowValidator;
    private final WorkflowExecutionService workflowExecutionService;
    private final SchedularService schedularService;

    public WorkflowController(PersistenceService persistenceService, WorkflowValidator workflowValidator,
            WorkflowExecutionService workflowExecutionService, SchedularService schedularService) {
        this.persistenceService = persistenceService;
        this.workflowValidator = workflowValidator;
        this.workflowExecutionService = workflowExecutionService;
        this.schedularService = schedularService;
    }

    @PostMapping
    public ResponseEntity<WorkflowDefinition> createWorkflow(@RequestBody WorkflowDefinition definition) {
        ValidationResult result = workflowValidator.validate(definition);
        if(!result.isValid()) {
            throw new IllegalArgumentException("Workflow validation failed" + result.getErrors());
        }
        WorkflowDefinition saved = persistenceService.saveWorkflow(definition);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<WorkflowDefinition>> listWorkflows() {
        return ResponseEntity.ok(persistenceService.getAllWorkflows());
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkflowDefinition> updateWorkflow(@PathVariable String id, @RequestBody WorkflowDefinition definition) {
        ValidationResult result = workflowValidator.validate(definition);
        if(!result.isValid()) {
            throw new IllegalArgumentException("workflow validation failed");
        }

        WorkflowDefinition updated = persistenceService.updateWorkflow(id, definition);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkflow(@PathVariable String id) {
        persistenceService.getWorkflowById(id).orElseThrow(() -> new NoSuchElementException("Workflow not found exception"));
        persistenceService.deleteWorkflow(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/execute-node")
    public ResponseEntity<Map<String, Object>> executeNode(@RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(workflowExecutionService.executeSingleNode(request));
    }

    

    
}
