package com.smartautomation.service;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AiChatServiceImpl implements AiChatService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AiChatServiceImpl.class);
    private static final String UNAVAILABLE_MESSAGE = "AI service is currently unavailable. Please try again later.";
    private static String SYSTEM_PROMPT = "You are a helpful assistant. You will be provided with a prompt, and you should respond with a concise and informative answer. If the prompt is unclear or ambiguous, ask for clarification. If the prompt is inappropriate or offensive, respond politely and refuse to answer. Your responses should be in English.";
    private final ChatModel chatModel;
    private final String ollamaBaseUrl;
    private final String ollamaModel;
    private final String nvidiaApiKey;
    private final String nvidiaBaseUrl;
    private final String nvidiaModel;
    private final Map<String, List<Message>> sessionContexts = new ConcurrentHashMap<>();

    public AiChatServiceImpl(ChatModel chatModel,
            @Value("${spring.ai.ollama.base-url:http://localhost:11434}") String ollamaBaseUrl,
            @Value("${spring.ai.ollama.chat.model:llama3.2}") String ollamaModel,
            @Value("${nvidia.api.key:}") String nvidiaApiKey,
            @Value("${nvidia.api.base-url:https://integrate.api.nvidia.com/v1}") String nvidiaBaseUrl,
            @Value("${nvidia.api.model:meta/llama-3.1-8b-instruct}") String nvidiaModel) {
        this.chatModel = chatModel;
        this.ollamaBaseUrl = ollamaBaseUrl;
        this.ollamaModel = ollamaModel;
        this.nvidiaApiKey = nvidiaApiKey;
        this.nvidiaBaseUrl = nvidiaBaseUrl;
        this.nvidiaModel = nvidiaModel;
    }
    

    @Override
    public AiChatResponse sendMessage(String sessionId, String message) {
        // Implement the logic to send a message to the AI chatbot and receive a response
        // For example, you can use an AI API or library to process the message and generate a response
        // Return the ChatResponse object containing the chatbot's reply
        try {
            List<Message> context = sessionContexts.computeIfAbsent(sessionId, k -> {
                List<Message> messages = new ArrayList<>();
                messages.add(new SystemMessage(SYSTEM_PROMPT));
                return messages;
            });
            context.add(new UserMessage(message));
            Prompt prompt = new Prompt(new ArrayList<>(context));
            ChatResponse aiResponse = chatModel.call(prompt);
            String responseText = aiResponse.getResult().getOutput().getContent();
            context.add(new AssistantMessage(responseText));
            return new AiChatResponse(responseText, sessionId, Instant.now());
        } catch (Exception e) {
            log.error("failed to get AI response for session {}: {}", sessionId, e.getMessage());
            return new AiChatResponse(UNAVAILABLE_MESSAGE, sessionId, Instant.now());
        }
    }

    @Override
    public AiChatResponse executeAsNode(String prompt) {
        // Implement the logic to execute a node in the AI chatbot
        // Return the AiChatResponse object containing the result
        return executeAsNode(prompt, null);
    }

    @Override
    public AiChatResponse executeAsNode(String prompt, String model) {
        // Implement the logic to execute a node in the AI chatbot with a specific model
        // Return the AiChatResponse object containing the result
        String nodeSessionId = "node-" + UUID.randomUUID();
        return executeViaHttp(prompt, nodeSessionId, model);
    }

    @Override
    public AiChatResponse executeWithNvidia(String prompt) {
        return executeWithNvidia(prompt, null);
    }

    @Override
    public AiChatResponse executeWithNvidia(String prompt, String model) {
        String nodeSessionId = "nvidia-" + UUID.randomUUID();
        return executeViaNvidia(prompt, nodeSessionId, model);
    }


    private AiChatResponse executeViaHttp(String prompt, String nodeSessionId, String model) {
        try {
            ObjectMapper om = new ObjectMapper();
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(30))
                    .build();
            String useModel = (model != null && !model.isBlank()) ? model : nvidiaModel;
            Map<String, Object> requestMap = new LinkedHashMap<>();
            requestMap.put("model", useModel);
            requestMap.put("messages", List.of(Map.of("role", "user", "content", prompt)));
            requestMap.put("stream", false);

            String requestBody = om.writeValueAsString(requestMap);
            log.info("Calling Ollama model {} with request body: {}", useModel, requestBody);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ollamaBaseUrl + "/api/chat"))
                    .timeout(Duration.ofMinutes(5))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String body = response.body();
            log.info("Ollama response status: {}, body: {}", response.statusCode(), body);
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                log.error("Ollama request failed with status {} and body: {}", response.statusCode(), body);
                return new AiChatResponse(UNAVAILABLE_MESSAGE, nodeSessionId, Instant.now());
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> responseMap = om.readValue(body, Map.class);
            @SuppressWarnings("unchecked")
            Map<String, Object> message = (Map<String, Object>) responseMap.get("message");
            Object content = message != null ? message.get("content") : null;
            if (content != null && !content.toString().isBlank()) {
                return new AiChatResponse(content.toString(), nodeSessionId, Instant.now());
            }

            log.error("Ollama response did not contain message.content: {}", body);
            return new AiChatResponse(UNAVAILABLE_MESSAGE, nodeSessionId, Instant.now());
        } catch (Exception e) {
            log.error("Failed to execute prompt through Ollama HTTP for session {}", nodeSessionId, e);
            return new AiChatResponse(UNAVAILABLE_MESSAGE, nodeSessionId, Instant.now());
        }
    }

    private AiChatResponse executeViaNvidia(String prompt, String nodeSessionId, String model) {
        if (nvidiaApiKey == null || nvidiaApiKey.isBlank()) {
            log.error("NVIDIA API key is not configured. Set nvidia.api.key or NVIDIA_API_KEY.");
            return new AiChatResponse(UNAVAILABLE_MESSAGE, nodeSessionId, Instant.now());
        }

        try {
            ObjectMapper om = new ObjectMapper();
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(30))
                    .build();

            String useModel = (model != null && !model.isBlank()) ? model : nvidiaModel;
            Map<String, Object> requestMap = new LinkedHashMap<>();
            requestMap.put("model", useModel);
            requestMap.put("messages", List.of(Map.of("role", "user", "content", prompt)));
            requestMap.put("temperature", 0.7);
            requestMap.put("max_tokens", 1024);
            requestMap.put("stream", false);

            String requestBody = om.writeValueAsString(requestMap);
            log.info("Calling NVIDIA NIM model {}", useModel);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(nvidiaBaseUrl + "/chat/completions"))
                    .timeout(Duration.ofMinutes(5))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + nvidiaApiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            log.info("NVIDIA response status: {}, body: {}", response.statusCode(), body);

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                log.error("NVIDIA request failed with status {} and body: {}", response.statusCode(), body);
                return new AiChatResponse(UNAVAILABLE_MESSAGE, nodeSessionId, Instant.now());
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> responseMap = om.readValue(body, Map.class);
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> choices = (List<Map<String, Object>>) responseMap.get("choices");

            if (choices != null && !choices.isEmpty()) {
                @SuppressWarnings("unchecked")
                Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                Object content = message != null ? message.get("content") : null;
                if (content != null && !content.toString().isBlank()) {
                    return new AiChatResponse(content.toString(), nodeSessionId, Instant.now());
                }
            }

            log.error("NVIDIA response did not contain choices[0].message.content: {}", body);
            return new AiChatResponse(UNAVAILABLE_MESSAGE, nodeSessionId, Instant.now());
        } catch (Exception e) {
            log.error("Failed to execute prompt through NVIDIA NIM for session {}", nodeSessionId, e);
            return new AiChatResponse(UNAVAILABLE_MESSAGE, nodeSessionId, Instant.now());
        }
    }

}
