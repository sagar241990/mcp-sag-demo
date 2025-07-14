package com.example.mcpserver.mcp_server.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ToolDefinitionLoader {

    public List<ToolCallback> loadToolDefinitionsAndConvertToToolBacks(String yamlFileName) {
        log.info("Loading tool definitions from YAML file: {}", yamlFileName);
        
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(yamlFileName)) {
            if (inputStream == null) {
                log.error("YAML file not found: {}", yamlFileName);
                throw new RuntimeException("YAML file not found: " + yamlFileName);
            }
            
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            ToolYamlConfig config = mapper.readValue(inputStream, ToolYamlConfig.class);
            
            log.debug("Successfully parsed YAML config with {} tools", config.getTools().size());

            List<ToolCallback> toolCallbacksList = new ArrayList<>();
            for (ToolYamlDefinition tool : config.getTools()) {
                log.debug("Processing tool: {}", tool.getName());

                ToolDefinition toolDefinition = ToolDefinition.builder()
                        .name(tool.getName())
                        .description(tool.getDescription())
                        .inputSchema(tool.getInputSchema())
                        .build();

                DynamicApiToolCallback toolCallback = new DynamicApiToolCallback(
                        toolDefinition,
                        "http://localhost:8083/api/customers", HttpMethod.GET);
                toolCallbacksList.add(toolCallback);
                
                log.debug("Successfully created callback for tool: {}", tool.getName());
            }

            log.info("Successfully loaded {} tool definitions from {}", toolCallbacksList.size(), yamlFileName);
            return toolCallbacksList;

        } catch (Exception e) {
            log.error("Failed to load tool definitions from YAML: {}", yamlFileName, e);
            throw new RuntimeException("Failed to load tool definitions from YAML: " + yamlFileName, e);
        }
    }

}
