package zhibi.fast.poi.excel.style;

import org.apache.poi.ss.usermodel.Workbook;
import zhibi.fast.poi.excel.style.cell.DefaultCellStyle;
import zhibi.fast.poi.excel.style.cell.ExcelCellStyle;

/**
 * excel样式的工具类
 * 反射得到
 * @author 执笔
 * @date 2018/11/23 21:47
 */
public class StyleUtils {


    /**
     * 得到excel的样式
     * 如果报错返回默认样式
     *
     * @param clazz
     * @return
     */
    public static ExcelStyle getExcelStyle(Class<? extends ExcelStyle> clazz, Workbook workbook) {
        try {
            return clazz.getConstructor(Workbook.class)
                    .newInstance(workbook);
        } catch (Exception e) {
            return new DefaultStyle(workbook);
        }
    }

    /**
     * 得到列样式
     *
     * @param clazz
     * @param workbook
     * @return
     */
    public static ExcelCellStyle getExcelCellStyle(Class<? extends ExcelCellStyle> clazz, Workbook workbook) {
        try {
            return clazz.getConstructor(Workbook.class)
                    .newInstance(workbook);
        } catch (Exception e) {
            return new DefaultCellStyle(workbook);
        }
    }

}
