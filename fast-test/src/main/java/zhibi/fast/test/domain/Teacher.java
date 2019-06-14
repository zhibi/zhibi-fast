package zhibi.fast.test.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import zhibi.fast.poi.excel.annotation.Excel;

/**
 * @author 执笔
 * @date 2019/4/16 17:02
 */
@Data
@Accessors(chain = true)
public class Teacher {
    /**
     *
     */
    @Excel("名字")
    private String name;
    /**
     *
     */
    @Excel("班级")
    private String clas;
}
