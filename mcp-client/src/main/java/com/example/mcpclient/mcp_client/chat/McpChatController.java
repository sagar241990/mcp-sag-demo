package com.example.mcpclient.mcp_client.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("mcp-chat")
@RestController
public class McpChatController {
    private final ChatClient chatClient;

    private final SyncMcpToolCallbackProvider syncMcpToolCallbackProvider;

    public McpChatController(ChatClient.Builder chatClientBuilder,
                             @Qualifier("my-mcp-server-callback-tool-provider") SyncMcpToolCallbackProvider syncMcpToolCallbackProvider) {
        log.info("Initializing McpChatController with ChatClient and MCP ToolCallbackProvider");
        this.chatClient = chatClientBuilder.build();
        this.syncMcpToolCallbackProvider = syncMcpToolCallbackProvider;
        log.info("McpChatController initialized successfully");
    }

    @PostMapping
    public String chat(@RequestBody String message) {
        log.info("Received chat message, length: {} characters", message.length());
        log.debug("Chat message content: {}", message);
        
        try {
            long startTime = System.currentTimeMillis();
            log.debug("Processing chat request with MCP tool callbacks");
            
            String response = chatClient.prompt()
                    .user(message)
                    .toolCallbacks(syncMcpToolCallbackProvider)
                    .call().content();
            
            long processingTime = System.currentTimeMillis() - startTime;
            log.info("Chat response generated successfully in {}ms, response length: {} characters", 
                       processingTime, response.length());
            log.debug("Chat response: {}", response);
            
            return response;
            
        } catch (Exception e) {
            log.error("Error processing chat message: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to process chat message", e);
        }
    }
}
