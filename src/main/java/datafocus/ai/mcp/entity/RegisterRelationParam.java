package datafocus.ai.mcp.entity;

import lombok.Data;

import java.util.List;

/**
 * @author sunc
 * @date 2025/2/19 18:03
 * @description RegisterRelationParam
 */

@Data
public class RegisterRelationParam {

    @Data
    public static class JoinCondition {
        private String srcColName;
        private String dstColName;
    }

    private String factTable;
    private String dimensionTable;

    private String joinType;
    private List<JoinCondition> conditions;

}
