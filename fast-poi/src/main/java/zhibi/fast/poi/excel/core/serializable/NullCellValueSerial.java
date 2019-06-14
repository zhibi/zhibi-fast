package zhibi.fast.poi.excel.core.serializable;

/**
 * @author 执笔
 * @date 2018/11/25 19:35
 */
public class NullCellValueSerial implements CellValueSerializable {
    @Override
    public String serial(Object val) {
        return "";
    }
}
