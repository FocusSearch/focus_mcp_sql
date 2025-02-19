package datafocus.ai.mcp.entity;

import lombok.Data;

import java.util.List;

/**
 * @author sunc
 * @date 2025/2/19 18:03
 * @description RegisterModelParam
 */
@Data
public class RegisterModelParam {

    /**
     * 数据库类型
     */
    private String type;
    private String version;
    private List<RegisterTableParam> tables;
    private List<RegisterRelationParam> relations;

}
