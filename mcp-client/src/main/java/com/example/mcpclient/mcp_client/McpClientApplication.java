package com.example.mcpclient.mcp_client;

import java.util.List;

import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.modelcontextprotocol.client.McpSyncClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class McpClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(McpClientApplication.class, args);
	}

	@Bean
	@Qualifier("my-mcp-server-callback-tool-provider")
	public SyncMcpToolCallbackProvider toolCallbackProvider(List<McpSyncClient> mcpSyncClients) {
		log.info("Creating MCP tool callback provider with {} client(s)", mcpSyncClients.size());
		SyncMcpToolCallbackProvider provider = new SyncMcpToolCallbackProvider(mcpSyncClients);
		log.info("MCP tool callback provider created successfully");
		return provider;
	}

}
