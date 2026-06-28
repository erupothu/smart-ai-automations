package com.smartautomation.connector;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.smartautomation.model.ConnectorMetadata;
import com.smartautomation.model.ConnectorOperation;

import jakarta.annotation.PostConstruct;

@Component
public class ConnectorRegistry {
    
    private final Map<String, Connector> connectors = new LinkedHashMap<>();
    private final Map<String, ConnectorMetadata> metadataMap = new LinkedHashMap<>();
    
    public void register(Connector connector) {
        connectors.put(connector.getType(), connector);
    }

    public Connector getByType(String type) {
        Connector connector = connectors.get(type);
        if(connector == null) {
            throw new IllegalArgumentException("No connector registered for type: "  + type);
        }
        return connector;
    }

    public List<ConnectorMetadata> getAllMetadata() {
        return new ArrayList<>(metadataMap.values());
    }

    public List<ConnectorMetadata> search(String query) {
        if(query ==null || query.isBlank()) {
            return getAllMetadata();
        }
        String lowerQuery = query.toLowerCase();
        return metadataMap.values().stream().filter(m -> m.getName().toLowerCase().contains(lowerQuery)
            || m.getCategory().toLowerCase().contains(lowerQuery))
            .collect(Collectors.toList());
    }

    @PostConstruct
    public void initMetadata() {
        registerMetadata("SCHEDULE", "schedule", "schedule-icon", "scheduling", 
            List.of(op("schedule-cron", "Cron Trigger", "Trigger on cron Scheudle")),
            List.of(), false);

        registerMetadata("GMAIL", "Gmail", "Gmail-icon", "communication", List.of(op("gmail-new-email", "new Email", "trigger on new Email")), 
            List.of(op("gmail-send-email", "send Email", "Send an Email")), true);

        registerMetadata("MYSQL", "Mysql", "mysql-icon", "Database", 
        List.of(op("mysql-execute-query", "Execute Query", "Execute Parameterized query")), 
            List.of(op("gmail-send-email", "send Email", "Send an Email")), true);

        registerMetadata("MONGODB", "Mongodb", "mongodb-icon", "Database", 
        List.of(), 
            List.of(op("mongodb-find", "find Document", "find document in collection"),
            op("mongodb-insert", "insert Document", "insert document in collection"),
            op("mongodb-update", "update Document", "update document in collection"),
            op("mongodb-delete", "delete Document", "delete document in collection")),
             false);

        registerMetadata("AGENTIC_AI_CREW", "Agentic Ai crew", "groups-icon", "AI", 
        List.of(), 
        List.of(op("agentic-crew-execute", "Execute AI Crew Task", "Run Agentic Ai task on workspace code")),
         false);

        
    }

    private void registerMetadata(String type, String name, String icon, String category,
        List<ConnectorOperation> triggerOps, List<ConnectorOperation> actionOps, boolean requiresAuth
    ) {
        ConnectorMetadata metadata = new ConnectorMetadata();
        metadata.setType(type);
        metadata.setName(name);
        metadata.setIcon(icon);
        metadata.setCategory(category);
        metadata.setTriggerOperations(triggerOps);
        metadata.setActionOperations(actionOps);
        metadata.setRequiresAuth(requiresAuth);
        metadataMap.put(type, metadata);
    }

    private ConnectorOperation op(String id, String name, String description) {
        return new ConnectorOperation(id, name, description,Map.of());
    }
}
