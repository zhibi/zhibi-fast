package zhibi.fast.poi.excel.export.config;

import lombok.Data;
import zhibi.fast.poi.excel.export.core.hanler.CellValueHandler;
import zhibi.fast.poi.excel.export.core.hanler.DefaultCellValueHandler;
import zhibi.fast.poi.excel.export.style.DefaultStyle;
import zhibi.fast.poi.excel.export.style.ExcelStyle;

/**
 * 数据导出配置
 *
 * @author 执笔
 * @date 2018/11/23 17:34
 */
@Data
public class ExcelSheetConfig {

    public ExcelSheetConfig(String sheetName) {
        this.sheetName = sheetName;
    }

    public ExcelSheetConfig(String sheetName, String title) {
        this.sheetName = sheetName;
        this.title = title;
    }

    public ExcelSheetConfig(String sheetName, String title, String secondTitle) {
        this.sheetName = sheetName;
        this.title = title;
        this.secondTitle = secondTitle;
    }

    /**
     * sheet的名字
     * 如果有重复将采用默认的
     */
    private String sheetName;

    /**
     * 大标题
     */
    private String title;

    /**
     * 二级标题
     */
    private String secondTitle;


    /**
     * 是否固定头部
     *
     * @return
     */
    private boolean fixTitle = true;

    /**
     * 是否固定表头
     *
     * @return
     */
    private boolean fixHead = true;

    /**
     * excel使用的样式
     */
    private Class<? extends ExcelStyle> excelStyle = DefaultStyle.class;

    /**
     * 值处理器
     */
    private CellValueHandler cellValueHandler = new DefaultCellValueHandler();
}
