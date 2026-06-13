package com.smartautomation.users.repository;

import com.smartautomation.users.entity.UserAuditDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserAuditRepository extends MongoRepository<UserAuditDocument, String> {
}
