package com.example.mcpserver.mcp_server;

import java.util.List;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.example.mcpserver.mcp_server.service.ToolDefinitionLoader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class McpServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(McpServerApplication.class, args);
	}

	/**
	 * This bean loads tool definitions from a YAML file and converts them to ToolCallback instances.
	 * The YAML file should be located in the classpath.
	 *
	 * @param loader ToolDefinitionLoader instance to load and convert tool definitions.
	 * @return List of ToolCallback instances.
	 */

	@Bean
	public List<ToolCallback> tools(ToolDefinitionLoader loader) {
		log.info("Loading tools bean from YAML configuration");
		List<ToolCallback> tools = loader.loadToolDefinitionsAndConvertToToolBacks("api-toolback-properties.yaml");
		log.info("Tools bean initialized successfully with {} tools", tools.size());
		return tools;
	}

}
