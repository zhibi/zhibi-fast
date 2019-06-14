package zhibi.fast.poi.excel.style.cell;

import lombok.Getter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author 执笔
 * @date 2018/11/25 12:13
 */
public abstract class AbstractCellStyle implements ExcelCellStyle {

    @Getter
    private CellStyle cellStyle;

    public AbstractCellStyle(Workbook workbook) {
        this.cellStyle = workbook.getCellStyleAt(0);
        if (null == this.cellStyle) {
            this.cellStyle = workbook.createCellStyle();
        }
        config(this.cellStyle);
    }


    @Override
    public void config(CellStyle cellStyle) {

    }
}
