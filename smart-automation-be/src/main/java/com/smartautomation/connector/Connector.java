package com.smartautomation.connector;

public interface Connector {

    String getType();

    List<ConnectorOperation> getTriggerOperations();
    
}
