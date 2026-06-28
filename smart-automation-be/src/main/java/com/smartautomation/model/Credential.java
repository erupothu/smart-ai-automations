package com.smartautomation.model;

import java.time.Instant;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;

@Document(collation = "credentials")
public class Credential {

    @Id
    private String id;

    private String name;

    private String connectorType;
    private AuthType authtype;
    private String encriptedData;
    private Instant createdAt;
    private Instant updatedAt;

    public Credential() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConnectorType() {
        return connectorType;
    }

    public void setConnectorType(String connectorType) {
        this.connectorType = connectorType;
    }

    public AuthType getAuthtype() {
        return authtype;
    }

    public void setAuthtype(AuthType authtype) {
        this.authtype = authtype;
    }

    public String getEncriptedData() {
        return encriptedData;
    }

    public void setEncriptedData(String encriptedData) {
        this.encriptedData = encriptedData;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    
    
}
