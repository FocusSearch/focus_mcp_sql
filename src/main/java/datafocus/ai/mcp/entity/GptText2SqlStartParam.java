package datafocus.ai.mcp.entity;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

/**
 * @author sunc
 * @date 2025/2/19 18:03
 * @description GptText2SqlStartParam
 */
@Data
public class GptText2SqlStartParam {

    private String language;
    private RegisterModelParam model;

    public static String buildBody(RegisterModelParam model, String language) {
        GptText2SqlStartParam param = new GptText2SqlStartParam();
        param.language = language;
        param.model = model;
        return JSONObject.toJSONString(param);
    }

}
