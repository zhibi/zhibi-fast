package zhibi.fast.mybatis.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 分页的基础
 *
 * @author 执笔
 * @date 2019/4/12 10:09
 */
@Getter
@Setter
public class BasePage implements Serializable {

    @Transient
    private Integer pageNum = 1;

    @Transient
    private Integer pageSize = 10;

    @Transient
    private Integer offset = 0;
    @Transient
    private Integer limit  = 10;
}
