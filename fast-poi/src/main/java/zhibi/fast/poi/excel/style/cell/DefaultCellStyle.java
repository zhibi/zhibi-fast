package zhibi.fast.poi.excel.style.cell;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * 默认的列样式
 *
 * @author 执笔
 * @date 2018/11/25 12:16
 */
public class DefaultCellStyle extends AbstractCellStyle implements ExcelCellStyle {

    public DefaultCellStyle(Workbook workbook) {
        super(workbook);
    }
}
