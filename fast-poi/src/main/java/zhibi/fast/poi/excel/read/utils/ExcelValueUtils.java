package zhibi.fast.poi.excel.read.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;

import java.util.TimeZone;

/**
 * 读取Excel的val值
 *
 * @author 执笔
 * @date 2019/7/30 14:59
 */
public class ExcelValueUtils {

    /**
     * 读取 xls 表格单元格的值
     *
     * @param cell
     * @return
     */
    public static Object readValue(Cell cell) {
        if (null == cell) {
            return "";
        }
        CellType cellType = cell.getCellType();


        switch (cellType) {
            case NUMERIC:
                // 时间处理
                if (DateUtil.isCellDateFormatted(cell)) {
                    TimeZone tz = TimeZone.getTimeZone("ETC/GMT-8");
                    TimeZone.setDefault(tz);
                    return cell.getDateCellValue();
                }
                return cell.getNumericCellValue();
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case ERROR:
                return cell.getErrorCellValue();
            default:
                return "";
        }
    }

}
