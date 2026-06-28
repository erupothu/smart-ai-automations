package com.smartautomation.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartautomation.connector.ConnectorRegistry;
import com.smartautomation.model.ConnectorMetadata;
import com.smartautomation.model.ConnectorOperation;

@RestController
@RequestMapping("/api/connectors")
public class ConnectorController {
    
    private final ConnectorRegistry connectorRegistry;

    public ConnectorController(ConnectorRegistry connectorRegistry) {
        this.connectorRegistry = connectorRegistry;
    }

    @GetMapping
    public ResponseEntity<List<ConnectorMetadata>> listConnectors() {
        return ResponseEntity.ok(connectorRegistry.getAllMetadata());
    }

    @GetMapping("/{type}/operations")
    public ResponseEntity<List<ConnectorOperation>> getOperations(@PathVariable String type) {
        List<ConnectorMetadata> allMetadata = connectorRegistry.getAllMetadata();
        ConnectorMetadata metadata = allMetadata.stream().filter(m -> m.getType().equals(type)).findFirst()
        .orElseThrow(() -> new NoSuchElementException("connector type not found: "+ type));

        List<ConnectorOperation> operations = new ArrayList<>();
        if(metadata.getTriggerOperations() != null) {
            operations.addAll(metadata.getTriggerOperations());
        }
        if(metadata.getActionOperations() != null) {
            operations.addAll(metadata.getActionOperations());
        }
        return ResponseEntity.ok(operations);
    }

    
}
