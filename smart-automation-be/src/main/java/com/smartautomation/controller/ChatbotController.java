package com.smartautomation.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartautomation.service.AiChatResponse;
import com.smartautomation.service.AiChatService;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {
    private final AiChatService aiChatService;

    public ChatbotController( @Autowired(required = false) AiChatService aiChatService) {
        this.aiChatService = aiChatService;
    }

    @PostMapping("/message")
    public ResponseEntity<?> sendMessage(@RequestBody Map<String, String> body) {
        String sessionId = body.get("sessionId");
        String message = body.get("message");

        if(sessionId == null || sessionId.isBlank()) {
            throw new IllegalArgumentException("Session ID is required");
        }
        if(message == null || message.isBlank()) {
            throw new IllegalArgumentException("Message is required");
        }

        return handleGeneralMode(sessionId, message);
    }

    private ResponseEntity<?> handleGeneralMode(String sessionId, String message) {
        if(aiChatService == null) {
            return ResponseEntity.status(503).body("AI service is currently unavailable. Please try again later.");
        }

        AiChatResponse response = aiChatService.sendMessage(sessionId, message);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/prompt")
    public ResponseEntity<?> sendPrompt(@RequestBody Map<String, String> body) {
        String prompt = body.get("prompt");
        String model = body.get("model");

        if(aiChatService == null) {
            return ResponseEntity.status(503).body("AI service is currently unavailable. Please try again later.");
        }
        if(prompt == null || prompt.isBlank()) {
            throw new IllegalArgumentException("Prompt is required");
        }

        StringBuilder aiPrompt = new StringBuilder();
        aiPrompt.append("User request: ").append(prompt);
        aiPrompt.append("\n\n response with only Json object");
        var chatResponse = aiChatService.executeAsNode(aiPrompt.toString(), model);
        String aiText = chatResponse.getMessage();
        return ResponseEntity.ok(aiText);
    }

    @PostMapping("/nvidia/prompt")
    public ResponseEntity<?> sendNvidiaPrompt(@RequestBody Map<String, String> body) {
        String prompt = body.get("prompt");
        String model = body.get("model");

        if(aiChatService == null) {
            return ResponseEntity.status(503).body("AI service is currently unavailable. Please try again later.");
        }
        if(prompt == null || prompt.isBlank()) {
            throw new IllegalArgumentException("Prompt is required");
        }

        StringBuilder aiPrompt = new StringBuilder();
        aiPrompt.append("User request: ").append(prompt);
        aiPrompt.append("\n\n response with only Json object");
        AiChatResponse response = aiChatService.executeWithNvidia(aiPrompt.toString(), model);
        return ResponseEntity.ok(response.getMessage());
    }

    @PostMapping("/nvidia/message")
    public ResponseEntity<?> sendNvidiaMessage(@RequestBody Map<String, String> body) {
        String message = body.get("message");
        String model = body.get("model");

        if(aiChatService == null) {
            return ResponseEntity.status(503).body("AI service is currently unavailable. Please try again later.");
        }
        if(message == null || message.isBlank()) {
            throw new IllegalArgumentException("Message is required");
        }

        AiChatResponse response = aiChatService.executeWithNvidia(message, model);
        return ResponseEntity.ok(response);
    }


}
