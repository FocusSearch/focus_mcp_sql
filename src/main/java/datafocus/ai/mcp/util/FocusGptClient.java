package datafocus.ai.mcp.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;

/**
 * @author sunc
 * @date 2025/2/19 18:03
 * @description FocusGptClient
 */
public class FocusGptClient {

    private static final String BASE_URL = "https://cloud001.datafocus.ai";

    private static final String GPT_TO_SQL_START_URI = "/df/rest/gpt/start";
    private static final String GPT_TO_SQL_CHAT_URI = "/df/rest/gpt/chat";

    /**
     * GPT 自然语言转SQL:初始化模型
     */
    public static String gptText2sqlStart(String body, String bearer) {
        return execute(GPT_TO_SQL_START_URI, body, bearer);
    }

    /**
     * GPT 自然语言转SQL:自然语言转SQL
     */
    public static String gptText2sqlChat(String body, String bearer) {
        return execute(GPT_TO_SQL_CHAT_URI, body, bearer);
    }

    private static String execute(String uri, String body, String bearer) {
        String url = BASE_URL + uri;
        HttpRequest request = HttpUtil.createPost(url);
        request.header("Authorization", StrUtil.format("Bearer {}", bearer)).body(body);
        HttpResponse response = request.execute();

        if (response.getStatus() == HttpStatus.HTTP_OK) {
            return response.body();
        }
        return "";
    }

}
