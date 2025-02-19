package datafocus.ai.mcp.entity;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

/**
 * @author sunc
 * @date 2025/2/19 18:03
 * @description GptText2SqlChatParam
 */
@Data
public class GptText2SqlChatParam {

    private String chatId;
    private String input;

    public static String buildBody(String chatId, String input) {
        GptText2SqlChatParam param = new GptText2SqlChatParam();
        param.chatId = chatId;
        param.input = input;
        return JSONObject.toJSONString(param);
    }
}
