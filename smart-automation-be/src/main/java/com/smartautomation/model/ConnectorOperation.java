package com.smartautomation.model;

import java.util.Map;

public class ConnectorOperation {
    
    private String operationId;
    private String name;
    private String description;
    private Map<String, Object> configSchema;
    
    public ConnectorOperation() {}

    public ConnectorOperation(String operationId, String name, String description, Map<String, Object> configSchema) {
        this.operationId = operationId;
        this.name = name;
        this.description = description;
        this.configSchema = configSchema;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Object> getConfigSchema() {
        return configSchema;
    }

    public void setConfigSchema(Map<String, Object> configSchema) {
        this.configSchema = configSchema;
    }

    


}
