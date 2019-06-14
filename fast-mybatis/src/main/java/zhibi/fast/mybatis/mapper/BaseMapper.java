package zhibi.fast.mybatis.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import zhibi.fast.mybatis.dto.BasePage;

/**
 * mysql 的 mapper
 *
 * @author 执笔
 * @date 2019/4/18 22:10
 */
public interface BaseMapper<T extends BasePage> extends Mapper<T>, MySqlMapper<T> {
}
