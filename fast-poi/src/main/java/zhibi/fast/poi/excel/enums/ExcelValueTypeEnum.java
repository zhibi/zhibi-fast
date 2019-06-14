package zhibi.fast.poi.excel.enums;

/**
 * 单元格值的类型
 * @author 执笔
 * @date 2018/11/26 11:44
 */
public enum ExcelValueTypeEnum {
    /**
     * 默认 处理类型
     * 根据具体类型自动判断
     */
    DEFAULT,

    /**
     * 当做文本处理
     * 对象或者集合，直接输出  不再有下级
     */
    TEXT,
    /**
     * 图片
     */
    IMG,
}
