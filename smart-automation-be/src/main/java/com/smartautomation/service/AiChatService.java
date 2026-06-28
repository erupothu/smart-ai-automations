package com.smartautomation.service;


public interface AiChatService {

    AiChatResponse sendMessage(String sessionId, String message);

    AiChatResponse executeAsNode(String prompt);

    AiChatResponse executeAsNode(String prompt, String model);

    AiChatResponse executeWithNvidia(String prompt);

    AiChatResponse executeWithNvidia(String prompt, String model);

}
