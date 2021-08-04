package priv.rabbit.vio.config.mybatis;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;


@Data
@ApiModel(value = "SqlHelperDTO", description = "编辑sqlDTO")
public class SqlHelperDTO {
    /**
     * 过滤条件
     */
    private String whereSql;
    /**
     * 是否添加到最后
     */
    @Builder.Default
    private Boolean isLast = false;

    /**
     * 通过表名查找（表名定位不唯一）
     */
    private String tableName;

    /**
     * 唯一别名（通过唯一别名精确定位）
     */
    private String alias;

    /**
     * 使用次数
     */
    @Builder.Default
    private AtomicInteger existCount = new AtomicInteger(0);

    /**
     * 允许执行次数
     */
    @Builder.Default
    private Integer allowCount = 1;

    public SqlHelperDTO() {
    }

    public SqlHelperDTO(String whereSql, String alias) {
        this.whereSql = whereSql;
        this.alias = alias;
    }

    public SqlHelperDTO(String whereSql, String alias, int allowCount) {
        this.whereSql = whereSql;
        this.alias = alias;
        this.allowCount = allowCount;
    }


    public SqlHelperDTO(String whereSql, Boolean isLast) {
        this.whereSql = whereSql;
        this.isLast = isLast;
    }

    @Builder
    public SqlHelperDTO(String whereSql, String alias, String tableName, Integer allowCount, boolean isLast) {
        this.whereSql = whereSql;
        this.alias = alias;
        this.tableName = tableName;
        this.allowCount = allowCount;
        this.isLast = isLast;
    }
}
