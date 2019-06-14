package zhibi.fast.poi.excel.style;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * 表格的样式
 * @author 执笔
 * @date 2018/11/24 21:54
 */
public interface ExcelStyle {

    /**
     * 标题的样式
     *
     * @return
     */
    CellStyle titleStyle();

    /**
     * 二级标题的样式
     *
     * @return
     */
    CellStyle secondTitleStyle();

    /**
     * 表头样式
     *
     * @return
     */
    CellStyle tableHeadStyle();

}
