package zhibi.fast.commons.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

/**
 * log MDC 操作
 *
 * @author 执笔
 * @date 2019/4/11 14:32
 */
public class LogMdcUtils {

    /**
     * 不存在的时候赋值
     *
     * @param key 标识
     * @param val 值
     */
    public static void putNotExist(String key, String val) {
        if (StringUtils.isEmpty(MDC.get(key))) {
            MDC.put(key, val);
        }
    }

    /**
     * 存在的时候移除
     *
     * @param key
     */
    public static void removeExist(String key) {
        if (StringUtils.isEmpty(MDC.get(key))) {
            MDC.remove(key);
        }
    }
}
