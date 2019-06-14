package zhibi.fast.mybatis.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import zhibi.fast.mybatis.dto.BaseDomain;
import zhibi.fast.mybatis.dto.BasePage;
import zhibi.fast.mybatis.example.MybatisExample;
import zhibi.fast.mybatis.mapper.BaseMapper;
import zhibi.fast.mybatis.service.BaseService;

import java.util.List;

/**
 * 基础service 实现
 *
 * @author 执笔
 */
public class BaseServiceImpl<E extends BaseMapper<T>, T extends BaseDomain> implements BaseService<T> {

    @Autowired
    protected E baseMapper;


    @Override
    public PageInfo<T> selectPage(T domain) {
        startPage(domain);
        return new PageInfo<>(baseMapper.select(domain));
    }

    @Override
    public PageInfo<T> selectPage(MybatisExample example) {
        startPage(example);
        return new PageInfo<>(baseMapper.selectByExample(example));
    }

    @Override
    public int merge(T dto) {
        if (dto.getId() == null) {
            return baseMapper.insertSelective(dto);
        } else {
            return baseMapper.updateByPrimaryKeySelective(dto);
        }
    }

    @Override
    public boolean isExist(MybatisExample example) {
        int count = baseMapper.selectCountByExample(example);
        return count != 0;
    }

    @Override
    public boolean isExist(T dto) {
        int count = baseMapper.selectCount(dto);
        return count != 0;
    }

    @Override
    public int insert(T domain) {
        return baseMapper.insertSelective(domain);
    }

    @Override
    public int update(T domain) {
        return baseMapper.updateByPrimaryKeySelective(domain);
    }

    @Override
    public int update(T domain, MybatisExample example) {
        return baseMapper.updateByExampleSelective(domain, example);
    }

    @Override
    public int deleteById(Long id) {
        return baseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int delete(T domain) {
        return baseMapper.delete(domain);
    }

    @Override
    public int delete(MybatisExample example) {
        return baseMapper.deleteByExample(example);
    }

    @Override
    public List<T> select(T domain) {
        if (null == domain) {
            return baseMapper.selectAll();
        }
        return baseMapper.select(domain);
    }

    @Override
    public List<T> select(MybatisExample example) {
        return baseMapper.selectByExample(example);
    }

    @Override
    public List<T> select(String column, Object value) {
        MybatisExample example = new MybatisExample()
                .eq(column, value);
        return select(example);
    }

    @Override
    public List<T> selectAll() {
        return baseMapper.selectAll();
    }

    @Override
    public T selectOne(T domain) {
        return baseMapper.selectOne(domain);
    }

    @Override
    public T selectById(Long id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    @Override
    public T selectOne(MybatisExample example) {
        return baseMapper.selectOneByExample(example);
    }

    @Override
    public T selectOne(String column, Object value) {
        MybatisExample example = new MybatisExample()
                .eq(column, value);
        return selectOne(example);
    }

    @Override
    public int selectCount(T domain) {
        return baseMapper.selectCount(domain);
    }

    @Override
    public int selectCount(MybatisExample example) {
        return baseMapper.selectCountByExample(example);
    }


    /**
     * 开始分页
     *
     * @param page
     */
    protected void startPage(BasePage page) {
        if (page.getLimit() != 10 || page.getOffset() != 0) {
            PageHelper.offsetPage(page.getOffset(), page.getLimit());
        } else {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
        }
    }
}
