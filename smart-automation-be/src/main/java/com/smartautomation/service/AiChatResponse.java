package com.smartautomation.service;

import java.time.Instant;

public class AiChatResponse {
    private String sessionId;
    private String message;
    private final Instant timestamp;

    public AiChatResponse(String message, String sessionId, Instant timestamp) {
        this.message = message;
        this.sessionId = sessionId;
        this.timestamp = Instant.now();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
