package com.example.mcpserver.mcp_server.service;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Getter
public class DynamicApiToolCallback implements ToolCallback {

    private final ToolDefinition toolDefinition;
    private final String apiUrl;
    private final HttpMethod httpMethod;

    @Override
    public String call(String toolInput) {
        
        log.info("Calling API: {} with method: {}", apiUrl, httpMethod);        
        log.debug("Tool input: {}", toolInput);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, httpMethod, null, String.class);
        return response.getBody();

    }

    @Override
    public String call(String toolInput, @Nullable ToolContext toolContext) {
    
        return call(toolInput);
    }

}
