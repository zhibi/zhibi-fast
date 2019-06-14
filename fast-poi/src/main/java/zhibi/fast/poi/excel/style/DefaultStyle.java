package zhibi.fast.poi.excel.style;

import org.apache.poi.ss.usermodel.*;

/**
 * 表格的默认样式
 *
 * @author 执笔
 * @date 2018/11/24 21:54
 */
public class DefaultStyle extends AbstractExcelStyle implements ExcelStyle {

    public DefaultStyle(Workbook workbook) {
        super(workbook);
    }

    @Override
    public CellStyle titleStyle() {
        CellStyle cellStyle = workbook.createCellStyle();
        Font      font      = workbook.createFont();
        /**
         * 设置字体
         */
        //加粗
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        /**
         * 对齐
         */
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFont(font);
        return cellStyle;
    }

    @Override
    public CellStyle secondTitleStyle() {
        CellStyle cellStyle = workbook.createCellStyle();
        Font      font      = workbook.createFont();
        /**
         * 设置字体
         */
        //加粗
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        /**
         * 对齐
         */
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFont(font);
        return cellStyle;
    }

    @Override
    public CellStyle tableHeadStyle() {
        CellStyle cellStyle = workbook.createCellStyle();
        Font      font      = workbook.createFont();
        /**
         * 设置字体
         */
        font.setFontHeightInPoints((short) 10);
        /**
         * 对齐
         */
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFont(font);
        return cellStyle;
    }

}
