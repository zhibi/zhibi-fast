package zhibi.fast.poi.excel.export.core.serializable;

import zhibi.fast.commons.base.RemarkEnum;

/**
 * 枚举值处理
 * @author 执笔
 * @date 2018/11/25 19:35
 */
public class EnumCellValueSerial implements CellValueSerializable {
    @Override
    public String serial(Object val) {
        if (val instanceof RemarkEnum) {
            return ((RemarkEnum) val).getRemark();
        }
        return String.valueOf(val);
    }
}
