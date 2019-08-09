package zhibi.fast.mybatis.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import tk.mybatis.mapper.annotation.KeySql;
import zhibi.fast.mybatis.annotation.AutoTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 执笔
 * 基础实体类
 */
@Getter
@Setter
public abstract class BaseDomain extends BasePage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @KeySql(useGeneratedKeys = true)
    private Long    id;
    /**
     * 操作人
     */
    private Long    operationId;
    @AutoTime(insert = true, update = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date    createTime;
    @AutoTime(insert = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date    updateTime;
}
