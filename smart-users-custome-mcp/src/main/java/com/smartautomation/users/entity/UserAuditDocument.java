package com.smartautomation.users.entity;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_audit")
public class UserAuditDocument {

    @Id
    private String id;
    private Long userId;
    private String action;
    private String email;
    private LocalDateTime actionAt;

    public UserAuditDocument() {
    }

    public UserAuditDocument(Long userId, String action, String email, LocalDateTime actionAt) {
        this.userId = userId;
        this.action = action;
        this.email = email;
        this.actionAt = actionAt;
    }

    public String getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getAction() {
        return action;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getActionAt() {
        return actionAt;
    }
}
