package zhibi.fast.poi.excel.export.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import zhibi.fast.poi.excel.export.core.serializable.CellValueSerializable;
import zhibi.fast.poi.excel.export.core.serializable.NullCellValueSerial;
import zhibi.fast.poi.excel.export.enums.ExcelValueTypeEnum;
import zhibi.fast.poi.excel.export.style.cell.DefaultCellStyle;
import zhibi.fast.poi.excel.export.style.cell.ExcelCellStyle;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 导出的
 *
 * @author 执笔
 * @date 2018/11/23 21:23
 */
@Data
@Accessors(chain = true)
public class ExportCell {

    /**
     * 列名
     */
    private String name;

    /**
     * 格式化规则
     */
    private String pattern;

    /**
     * 这一列是复合列
     */
    private List<ExportCell> list;

    /**
     * 对应的属性
     * 反射取值
     */
    private Field field;

    /**
     * 列宽度
     */
    private Integer width = 256 * 10;

    /**
     * 列样式
     */
    private Class<? extends ExcelCellStyle> cellStyle = DefaultCellStyle.class;

    /**
     * 排序  从小到大
     */
    private Integer sort = 0;


    /**
     * 需要合并几行
     */
    private Integer mergeRow = 1;

    /**
     * 需要合并几列
     */
    private Integer mergeCell = 1;

    /**
     * 一共几行
     * 合并行零时保存使用
     */
    private Integer totalRow = 1;

    /**
     * 当前列的索引
     * 合并单元格零时使用
     */
    private Integer cellIndex = 0;

    /**
     * 值处理器
     */
    private Class<? extends CellValueSerializable> valueSerial = NullCellValueSerial.class;

    /**
     * 值类型
     */
    private ExcelValueTypeEnum valueType = ExcelValueTypeEnum.DEFAULT;
}
