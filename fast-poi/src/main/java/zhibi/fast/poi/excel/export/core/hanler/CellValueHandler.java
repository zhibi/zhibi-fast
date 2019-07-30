package zhibi.fast.poi.excel.export.core.hanler;

import org.apache.poi.ss.usermodel.Cell;
import zhibi.fast.poi.excel.export.entity.ExportCell;

/**
 * 列值处理
 *
 * @author 执笔
 * @date 2018/11/25 19:04
 */
public interface CellValueHandler {
    /**
     * 插入列数据
     *
     * @param exportCell
     * @param cell 插入的单元格
     * @param value 插入的值
     */
    void insertValue(ExportCell exportCell, Cell cell, Object value);
}
