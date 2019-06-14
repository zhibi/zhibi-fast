package zhibi.fast.generate.config;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.sql.DataSource;

/**
 * 数据源配置
 *
 * @author 执笔
 */
@Data
@Accessors(chain = true)
public class DataSourceConfig {

    /**
     * 数据源
     */
    private DataSource dataSource;
    /**
     * 时候删除多余的列
     */
    private Boolean    delOverColumn = false;
}
