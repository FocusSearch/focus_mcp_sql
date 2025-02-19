package datafocus.ai.mcp.entity;

import lombok.Data;

import java.util.List;

/**
 * @author sunc
 * @date 2025/2/19 18:03
 * @description RegisterTableParam
 */
@Data
public class RegisterTableParam {
    private Long id;
    private String tableName;
    private String tableDisplayName;
    private String from;
    private List<RegisterColumnParam> columns;
}
