package com.smartautomation.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartautomation.model.Credential;
import com.smartautomation.service.CredentialService;

@RestController
@RequestMapping("/api/credentials")
public class CredentialController {
    
    private final CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping
    public ResponseEntity<Credential> saveCredential(@RequestBody Credential credential) {
        Credential saved = credentialService.save(credential);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Credential>> listCredentials() {
        return ResponseEntity.ok(credentialService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Credential> getCredentials(@PathVariable String id) {
        Credential credential = credentialService.getById(id);
        return ResponseEntity.ok(credential);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Credential> updateCredential(@PathVariable String id, @RequestBody Credential credential) {
        Credential updated = credentialService.update(id, credential);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCredential(@PathVariable String id) {
        credentialService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/test-connection")
    public ResponseEntity<Map<String, Object>> testConnection(@RequestBody Map<String, String> body) {
        String connectorType = body.get("connectorType");
        String encryptedData = body.get("encryptedData");

        return ResponseEntity.ok(null);
    }

}
