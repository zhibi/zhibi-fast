package zhibi.fast.poi.excel.annotation;

import zhibi.fast.poi.excel.core.serializable.CellValueSerializable;
import zhibi.fast.poi.excel.core.serializable.NullCellValueSerial;
import zhibi.fast.poi.excel.enums.ExcelValueTypeEnum;
import zhibi.fast.poi.excel.style.cell.AbstractCellStyle;
import zhibi.fast.poi.excel.style.cell.DefaultCellStyle;

import java.lang.annotation.*;

/**
 * 实体类属性上面的注解 设置导出时候的一些属性
 *
 * @author 执笔
 * @date 2018/11/23 17:09
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel {

    /**
     * 导出列名的名字
     *
     * @return
     */
    String value();

    /**
     * 格式化规则
     *
     * @return
     */
    String pattern() default "";

    /**
     * 列宽度
     * 256是一个字符的宽度  最大  256*256
     *
     * @return
     */
    int width() default 256 * 10;

    /**
     * 列样式
     *
     * @return
     */
    Class<? extends AbstractCellStyle> cellStyle() default DefaultCellStyle.class;

    /**
     * 排序  从小到大
     *
     * @return
     */
    int sort() default 0;

    /**
     * 值处理器
     *
     * @return
     */
    Class<? extends CellValueSerializable> valueSerial() default NullCellValueSerial.class;


    /**
     * 值的类型
     * 文字  图片  自动判断
     *
     * @return
     */
    ExcelValueTypeEnum valueType() default ExcelValueTypeEnum.DEFAULT;
}
