package com.example.mcpclient.mcp_client.chat;

import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class McpToolLister implements CommandLineRunner {

    private final List<McpSyncClient> mcpClients;

    @Override
    public void run(String... args) throws Exception {
        log.info("Discovering MCP Tools from {} client(s):", mcpClients.size());
        
        for (McpSyncClient client : mcpClients) {
            log.info("Connected to MCP Client: {}", client.getClientInfo().name());

            // Use the provider to get ToolCallbacks from the client
            SyncMcpToolCallbackProvider provider = new SyncMcpToolCallbackProvider(List.of(client));
            List<ToolCallback> toolCallbacks = List.of(provider.getToolCallbacks());

            if (toolCallbacks.isEmpty()) {
                log.warn("No tools found on MCP client: {}", client.getClientInfo().name());
            } else {
                log.info("Found {} tool(s) on client: {}", toolCallbacks.size(), client.getClientInfo().name());
                for (ToolCallback toolCallback : toolCallbacks) {
                    ToolDefinition toolDefinition = toolCallback.getToolDefinition();
                    log.info("--------------------");
                    log.info("  Tool Name: {}", toolDefinition.name());
                    log.info("  Description: {}", toolDefinition.description());
                    log.debug("  Input Schema: {}", toolDefinition.inputSchema());
                    log.info("--------------------");
                }
            }
        }
        
        log.info("MCP tool discovery completed");
    }
}