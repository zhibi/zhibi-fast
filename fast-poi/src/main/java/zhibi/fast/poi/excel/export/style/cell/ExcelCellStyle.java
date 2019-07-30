package zhibi.fast.poi.excel.export.style.cell;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * 单元格的样式
 * @author 执笔
 * @date 2018/11/25 12:34
 */
public interface ExcelCellStyle {

    /**
     * 配置样式
     *
     * @param cellStyle
     */
    void config(CellStyle cellStyle);

    /**
     * 得到样式
     *
     * @return
     */
    CellStyle getCellStyle();
}
