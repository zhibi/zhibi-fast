package zhibi.fast.mybatis.example;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import zhibi.fast.mybatis.dto.BasePage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 执笔
 * 组装mybatis请求条件
 */
@Getter
public class MybatisExample extends BasePage {

    /**
     * 是否排除重复的
     */
    private boolean distinct;

    /**
     * 排序语句
     */
    private String orderByClause = null;

    /**
     * 多组查询条件
     */
    private List<Criteria> oredCriteria;

    /**
     * 一组查询条件
     */
    private Criteria criteria;

    public MybatisExample() {
        oredCriteria = new ArrayList<>();
        criteria = new Criteria();
        oredCriteria.add(criteria);
    }


    /**
     * 添加  or 语句
     *
     * @param wrapper
     * @return
     */
    public MybatisExample or(MybatisExample wrapper) {
        oredCriteria.addAll(wrapper.getOredCriteria());
        return this;
    }

    /**
     * column = value
     *
     * @param column
     * @param value
     * @return
     */
    public MybatisExample eq(String column, Object value) {
        criteria.addCriterion(column + " =", value);
        return this;
    }

    /**
     * column <> value
     *
     * @param column
     * @param value
     * @return
     */
    public MybatisExample eqNot(String column, Object value) {
        criteria.addCriterion(column + " <>", value);
        return this;
    }

    /**
     * column between value1,value2
     *
     * @param column
     * @param value1
     * @param value2
     * @return
     */
    public MybatisExample between(String column, Object value1, Object value2) {
        criteria.addCriterion(column + " BETWEEN ", value1, value2);
        return this;
    }

    /**
     * column > value
     *
     * @param column
     * @param value
     * @return
     */
    public MybatisExample gt(String column, Object value) {
        criteria.addCriterion(column + " >", value);
        return this;
    }

    /**
     * column >= value
     *
     * @param column
     * @param value
     * @return
     */
    public MybatisExample gte(String column, Object value) {
        criteria.addCriterion(column + " >=", value);
        return this;
    }

    /**
     * column < value
     *
     * @param column
     * @param value
     * @return
     */
    public MybatisExample lt(String column, Object value) {
        criteria.addCriterion(column + " <", value);
        return this;
    }

    /**
     * column <= value
     *
     * @param column
     * @param value
     * @return
     */
    public MybatisExample lte(String column, Object value) {
        criteria.addCriterion(column + " <=", value);
        return this;
    }

    /**
     * column is null
     *
     * @param column
     * @return
     */
    public MybatisExample isNull(String column) {
        criteria.addCriterion(column + " IS NULL");
        return this;
    }

    /**
     * column is not null
     *
     * @param column
     * @return
     */
    public MybatisExample isNotNull(String column) {
        criteria.addCriterion(column + " IS NOT NULL");
        return this;
    }

    /**
     * column in (1,2,3)
     *
     * @param column
     * @param values
     * @return
     */
    public MybatisExample in(String column, List<Object> values) {
        if (values == null || values.size() == 0) {
            return this;
        }
        criteria.addCriterion(column + " IN", values);
        return this;
    }


    /**
     * column not in (1,2,3)
     *
     * @param column
     * @param values
     * @return
     */
    public MybatisExample notIn(String column, List<Object> values) {
        if (values == null || values.size() == 0) return this;
        criteria.addCriterion(column + " NOT IN", values);
        return this;
    }

    /**
     * column like '%value%'
     *
     * @param column
     * @param value
     * @return
     */
    public MybatisExample like(String column, String value) {
        if (StringUtils.isBlank(value)) {
            return this;
        }
        criteria.addCriterion(column + " LIKE '%" + value + "%'");
        return this;
    }

    /**
     * 自定义条件
     *
     * @param condition
     * @return
     */
    public MybatisExample condition(String condition) {
        criteria.addCriterion(condition);
        return this;
    }


    /**
     * 添加排序
     *
     * @param order
     * @param asc   是否顺序
     */
    public MybatisExample order(String order, boolean asc) {
        orderByClause = orderByClause == null ? "" : orderByClause + " ,";
        orderByClause = orderByClause + " " + order + " " + (asc ? "ASC" : "DESC");
        return this;
    }

    /**
     * 添加排序
     *
     * @param order
     */
    public MybatisExample order(String order) {
        return order(order, true);
    }

    /**
     * 分页
     *
     * @return
     */
    public MybatisExample page(BasePage page) {
        this.setPageNum(page.getPageNum());
        this.setPageSize(page.getPageSize());
        this.setLimit(page.getLimit());
        this.setOffset(page.getOffset());
        return this;
    }

    /**
     * 分页
     *
     * @return
     */
    public MybatisExample page(Integer pageNum, Integer pageSize) {
        this.setPageNum(pageNum);
        this.setPageSize(pageSize);
        return this;
    }

    /**
     * 分页
     *
     * @param limit
     * @param offset
     * @return
     */
    public MybatisExample pageLimit(Integer limit, Integer offset) {
        this.setLimit(limit);
        this.setOffset(offset);
        return this;
    }
}