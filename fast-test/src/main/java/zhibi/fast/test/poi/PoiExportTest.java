package zhibi.fast.test.poi;

import zhibi.fast.poi.excel.ExcelExport;
import zhibi.fast.poi.excel.enums.ExcelTypeEnum;
import zhibi.fast.test.domain.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 导出测试
 *
 * @author 执笔
 * @date 2019/4/16 16:16
 */
public class PoiExportTest {

    public static void main(String[] args) throws IOException {
        ExcelExport excelExport = new ExcelExport(ExcelTypeEnum.XLS);
        List<User>  users       = new ArrayList<>();
        for (int i = 0; i < 202; i++) {
            users.add(new User().setName(i + "-"));
        }
        excelExport.addSheet(users).export(new FileOutputStream("F:\\test.xls"));
    }
}
