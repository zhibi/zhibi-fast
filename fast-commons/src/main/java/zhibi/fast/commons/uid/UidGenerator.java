package zhibi.fast.commons.uid;

/**
 * @author 执笔
 */
public interface UidGenerator {

    /**
     * 得到执笔UID
     *
     * @return UID
     */
    long getUID();

    /**
     * 解析UID
     *
     * @param uid 需要解析的UID
     * @return Parsed info
     */
    String parseUID(long uid);

}
