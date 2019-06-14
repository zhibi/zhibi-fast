package zhibi.fast.test.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import zhibi.fast.poi.excel.annotation.Excel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 执笔
 * @date 2019/4/16 16:18
 */
@Data
@Accessors(chain = true)
public class User {

    /**
     *
     */
    @Excel("用户名")
    private String name;

    /**
     *
     */
    @Excel("地址")
    private List<Address> addressList = new ArrayList<Address>(){{
        add(new Address().setAge(123).setName("sdsds"));
        add(new Address().setAge(123).setName("s辅导费发送dsds"));
        add(new Address().setAge(123).setName("c"));
        add(new Address().setAge(123).setName("发送"));
        add(new Address().setAge(123).setName("发送的市人大"));
    }};
    @Excel("教师")
    private Teacher teacher = new Teacher().setName("sss").setClas("中曦");

    /**
     *
     */
    @Excel(value = "头像"/*,valueType = ExcelValueTypeEnum.IMG*/)
    private String icon = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3967239004,1951414302&fm=27&gp=0.jpg";
}
