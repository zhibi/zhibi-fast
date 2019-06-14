package zhibi.fast.poi.excel.core.serializable;

/**
 * 列值序列化
 *
 * @author 执笔
 * @date 2018/11/25 19:27
 */
public interface CellValueSerializable {

    /**
     * 值序列化
     *
     * @param val 值
     * @return
     */
    String serial(Object val);
}
