package datafocus.ai.mcp.server;

import datafocus.ai.mcp.entity.GptText2SqlChatParam;
import datafocus.ai.mcp.entity.GptText2SqlStartParam;
import datafocus.ai.mcp.entity.RegisterModelParam;
import datafocus.ai.mcp.util.FocusGptClient;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

/**
 * @author sunc
 * @date 2025/2/19 18:03
 * @description FocusGptService
 */
@Service
public class FocusGptService {

    /**
     * GPT 自然语言转SQL:初始化模型
     *
     * @param model    模型信息
     * @param language 语言
     * @param bearer   bearer token
     */
    @Tool(description = "自然语言转SQL:初始化模型")
    public String gptText2sqlStart(@ToolParam(description = "模型信息") RegisterModelParam model,
                                   @ToolParam(description = "上下文语言, 取值为 english 或者 chinese, 默认是 chinese") String language,
                                   @ToolParam(description = "鉴权token") String bearer) {
        String body = GptText2SqlStartParam.buildBody(model, language);
        return FocusGptClient.gptText2sqlStart(body, bearer);
    }

    /**
     * GPT 自然语言转SQL:解析自然语言, 转换为sql语句
     *
     * @param chatId 初始化模型的对话id
     * @param input  用户输入
     * @param bearer bearer token
     */
    @Tool(description = "自然语言转SQL:解析自然语言, 转换为SQL")
    public String gptText2sqlChat(@ToolParam(description = "初始化模型返回的对话id") String chatId,
                                  @ToolParam(description = "需要转换为sql的自然语言") String input,
                                  @ToolParam(description = "鉴权token") String bearer) {
        String body = GptText2SqlChatParam.buildBody(chatId, input);
        return FocusGptClient.gptText2sqlChat(body, bearer);
    }

}
