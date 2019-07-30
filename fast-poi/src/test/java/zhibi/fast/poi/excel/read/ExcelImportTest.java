package zhibi.fast.poi.excel.read;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author 执笔
 * @date 2019/7/30 16:07
 */
public class ExcelImportTest {

    @Test
    public void importToBean() throws IOException {
        List<User> users = ExcelImport.importToBean("F:\\Temp\\123.xls", User.class, 0, 0);
        System.out.println(users);

        users = ExcelImport.importToBean("F:\\Temp\\123.xlsx", User.class, 0, 0);
        System.out.println(users);
    }
}