package datafocus.ai.mcp.entity;

import lombok.Data;

import java.util.List;

/**
 * @author sunc
 * @date 2025/2/19 18:03
 * @description RegisterColumnParam
 */
@Data
public class RegisterColumnParam {
    private Long id;
    private String columnName;
    private String columnDisplayName;
    private String dataType;
    private String aggregation;
    private List<String> samples;
    private String statistics;
}
