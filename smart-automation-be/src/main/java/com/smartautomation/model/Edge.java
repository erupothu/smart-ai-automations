package com.smartautomation.model;

import org.springframework.data.mongodb.core.mapping.Field;

public class Edge {
    
    @Field("edgeId")
    private String edgeId;
    private String sourceNodeId;
    private String targetNodeId;

    public Edge() {}

    public Edge(String edgeId, String sourceNodeId, String targetNodeId) {
        this.edgeId = edgeId;
        this.sourceNodeId = sourceNodeId;
        this.targetNodeId = targetNodeId;
    }

    public String getEdgeId() {
        return edgeId;
    }

    public void setEdgeId(String edgeId) {
        this.edgeId = edgeId;
    }

    public String getSourceNodeId() {
        return sourceNodeId;
    }

    public void setSourceNodeId(String sourceNodeId) {
        this.sourceNodeId = sourceNodeId;
    }

    public String getTargetNodeId() {
        return targetNodeId;
    }

    public void setTargetNodeId(String targetNodeId) {
        this.targetNodeId = targetNodeId;
    }

    
}
