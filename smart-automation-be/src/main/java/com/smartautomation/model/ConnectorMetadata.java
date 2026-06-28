package com.smartautomation.model;

import java.util.List;

public class ConnectorMetadata {
    
    private String type;
    private String name;
    private String icon;
    private String category;
    private List<ConnectorOperation> triggerOperations;
    private List<ConnectorOperation> actionOperations;
    private boolean requiresAuth;

    public ConnectorMetadata() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<ConnectorOperation> getTriggerOperations() {
        return triggerOperations;
    }

    public void setTriggerOperations(List<ConnectorOperation> triggerOperations) {
        this.triggerOperations = triggerOperations;
    }

    public List<ConnectorOperation> getActionOperations() {
        return actionOperations;
    }

    public void setActionOperations(List<ConnectorOperation> actionOperations) {
        this.actionOperations = actionOperations;
    }

    public boolean isRequiresAuth() {
        return requiresAuth;
    }

    public void setRequiresAuth(boolean requiresAuth) {
        this.requiresAuth = requiresAuth;
    }

    

}
