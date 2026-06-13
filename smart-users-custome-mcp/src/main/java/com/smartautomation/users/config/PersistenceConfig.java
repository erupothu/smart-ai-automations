package com.smartautomation.users.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.smartautomation.users.repository")
@EnableMongoRepositories(basePackages = "com.smartautomation.users.repository")
public class PersistenceConfig {
}