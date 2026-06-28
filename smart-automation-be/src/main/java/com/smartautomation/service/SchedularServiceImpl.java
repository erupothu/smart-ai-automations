package com.smartautomation.service;

import org.springframework.stereotype.Service;

@Service
public class SchedularServiceImpl implements SchedularService{

    @Override
    public void registerSchedular(String workflowId, String cronExpression) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registerSchedular'");
    }

    @Override
    public void unregisteredSchedule(String workflowId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unregisteredSchedule'");
    }

    @Override
    public boolean isVaidCron(String cronExpression) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isVaidCron'");
    }

    @Override
    public String vaidateCron(String cronExpression) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'vaidateCron'");
    }
    
}
