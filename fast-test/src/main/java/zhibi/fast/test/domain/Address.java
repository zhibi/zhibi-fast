package zhibi.fast.test.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import zhibi.fast.poi.excel.annotation.Excel;

/**
 * @author 执笔
 * @date 2019/4/16 17:01
 */
@Data
@Accessors(chain = true)
public class Address {
    /**
     *
     */
    @Excel("名字")
    private String name;

    /**
     *
     */
    @Excel("年龄")
    private Integer age;
}
