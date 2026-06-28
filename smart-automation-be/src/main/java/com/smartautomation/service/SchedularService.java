package com.smartautomation.service;

public interface SchedularService {
    
    void registerSchedular(String workflowId, String cronExpression);
    void unregisteredSchedule(String workflowId);
    boolean isVaidCron(String cronExpression);
    String vaidateCron(String cronExpression);
    
}
