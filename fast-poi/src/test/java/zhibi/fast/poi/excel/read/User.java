package zhibi.fast.poi.excel.read;

import lombok.Data;

import java.util.Date;

/**
 * @author 执笔
 * @date 2019/7/30 16:08
 */
@Data
public class User {

    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private String name;

    private Date addTime;

    /**
     *
     */
    private Integer age;
}
