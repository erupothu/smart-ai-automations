package com.smartautomation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartautomation.model.TransientRunState;

@Repository
public interface TransientStateRepository extends JpaRepository<TransientRunState, String> {
    
    List<TransientRunState> findByWorkflowId(String workflowId);
    Optional<TransientRunState> findByRunId(String runId);
}
