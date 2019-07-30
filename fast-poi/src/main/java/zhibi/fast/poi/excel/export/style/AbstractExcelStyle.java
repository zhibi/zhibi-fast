package zhibi.fast.poi.excel.export.style;

import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 表格样式的抽象实现
 * @author 执笔
 * @date 2018/11/25 12:02
 */
@AllArgsConstructor
public abstract class AbstractExcelStyle implements ExcelStyle {

    protected Workbook workbook;

    @Override
    public CellStyle titleStyle() {
        return workbook.createCellStyle();
    }

    @Override
    public CellStyle secondTitleStyle() {
        return workbook.createCellStyle();
    }

    @Override
    public CellStyle tableHeadStyle() {
        return workbook.createCellStyle();
    }

}
