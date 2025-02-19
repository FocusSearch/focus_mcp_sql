package datafocus.ai.mcp.server;

import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.transport.StdioServerTransport;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.ai.mcp.McpToolUtils;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author sunc
 * @date 2025/2/19 18:03
 * @description McpServerApplication
 */
@SpringBootApplication
public class McpServerApplication {
    private static final String MCP_SERVER_NAME = "focus-mcp-sql-server";
    private static final String MCP_SERVER_VERSION = "1.0.0";

    public static void main(String[] args) {
        SpringApplication.run(McpServerApplication.class, args);
    }

    @Bean
    public List<ToolCallback> tools(FocusGptService focusGptService) {
        return List.of(ToolCallbacks.from(focusGptService));
    }

    @Bean
    public McpSyncServer mcpSyncServer(ObjectProvider<List<ToolCallback>> toolCalls, ObjectProvider<List<McpServerFeatures.SyncResourceRegistration>> resources, ObjectProvider<List<McpServerFeatures.SyncPromptRegistration>> prompts, ObjectProvider<Consumer<List<McpSchema.Root>>> rootsChangeConsumers) {
        List<ToolCallback> tools = toolCalls.stream().flatMap(Collection::stream).toList();
        McpSchema.ServerCapabilities.Builder capabilitiesBuilder = McpSchema.ServerCapabilities.builder();
        McpSchema.Implementation serverInfo = new McpSchema.Implementation(MCP_SERVER_NAME, MCP_SERVER_VERSION);
        McpServer.SyncSpec serverBuilder = McpServer.sync(new StdioServerTransport()).serverInfo(serverInfo);
        List<McpServerFeatures.SyncToolRegistration> toolRegistrations = McpToolUtils.toSyncToolRegistration(tools);
        if (!CollectionUtils.isEmpty(toolRegistrations)) {
            serverBuilder.tools(toolRegistrations);
            capabilitiesBuilder.tools(true);
            System.out.println("Registered tools[" + toolRegistrations.size() + "]");
        }

        List<McpServerFeatures.SyncResourceRegistration> resourceRegistrations = resources.stream().flatMap(Collection::stream).toList();
        if (!CollectionUtils.isEmpty(resourceRegistrations)) {
            serverBuilder.resources(resourceRegistrations);
            capabilitiesBuilder.resources(false, true);
            System.out.println("Registered resources[" + resourceRegistrations.size() + "]");
        }

        List<McpServerFeatures.SyncPromptRegistration> promptRegistrations = prompts.stream().flatMap(Collection::stream).toList();
        if (!CollectionUtils.isEmpty(promptRegistrations)) {
            serverBuilder.prompts(promptRegistrations);
            capabilitiesBuilder.prompts(true);
            System.out.println("Registered prompts[" + promptRegistrations.size() + "]");
        }

        rootsChangeConsumers.ifAvailable((consumer) -> {
            serverBuilder.rootsChangeConsumer(consumer);
            System.out.println("Registered roots change consumer");
        });
        serverBuilder.capabilities(capabilitiesBuilder.build());
        return serverBuilder.build();
    }

}
