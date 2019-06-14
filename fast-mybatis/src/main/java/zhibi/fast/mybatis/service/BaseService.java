package zhibi.fast.mybatis.service;

import com.github.pagehelper.PageInfo;
import zhibi.fast.mybatis.dto.BaseDomain;
import zhibi.fast.mybatis.example.MybatisExample;

import java.util.List;

/**
 * 基础service
 *
 * @param <T>
 * @author 执笔
 */
public interface BaseService<T extends BaseDomain> {

    /**
     * 分页
     *
     * @param domain
     * @return
     */
    PageInfo<T> selectPage(T domain);

    /**
     * 分页
     *
     * @param example
     * @return
     */
    PageInfo<T> selectPage(MybatisExample example);

    /**
     * 合并对象
     * 根据id判断是更新还是插入
     *
     * @param dto
     * @return
     */
    int merge(T dto);

    /**
     * 根据条件判断是否存在判断是否存在
     *
     * @param example
     * @return
     */
    boolean isExist(MybatisExample example);

    /**
     * 判断对象是否存在
     *
     * @param domain
     * @return
     */
    boolean isExist(T domain);

    /**
     * 插入数据库
     *
     * @param domain
     * @return
     */
    int insert(T domain);

    /**
     * 根据id更新
     *
     * @param domain
     * @return
     */
    int update(T domain);


    /**
     * 根据条件更新
     *
     * @param domain
     * @param example
     * @return
     */
    int update(T domain, MybatisExample example);


    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 删除
     *
     * @param domain
     * @return
     */
    int delete(T domain);

    /**
     * 根据条件删除
     *
     * @param example
     * @return
     */
    int delete(MybatisExample example);

    /**
     * 查询
     *
     * @param domain
     * @return
     */
    List<T> select(T domain);

    /**
     * 根据条件查找
     *
     * @param example
     * @return
     */
    List<T> select(MybatisExample example);

    /**
     * 根据列查找
     *
     * @param column
     * @param value
     * @return
     */
    List<T> select(String column, Object value);

    /**
     * 全部数据
     *
     * @return
     */
    List<T> selectAll();

    /**
     * 查找一个
     *
     * @param domain
     * @return
     */
    T selectOne(T domain);

    /**
     * 根据id查找一个
     *
     * @param id
     * @return
     */
    T selectById(Long id);

    /**
     * 根据条件查找一个
     *
     * @param example
     * @return
     */
    T selectOne(MybatisExample example);

    /**
     * 根据列查找一个
     *
     * @param column
     * @param value
     * @return
     */
    T selectOne(String column, Object value);


    /**
     * 根据条件查找数量
     *
     * @param domain
     * @return
     */
    int selectCount(T domain);

    /**
     * 根据条件查找数量
     *
     * @param example
     * @return
     */
    int selectCount(MybatisExample example);

}
