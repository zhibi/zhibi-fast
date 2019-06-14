package zhibi.fast.commons.context;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局系统参数
 *
 * @author 执笔
 */
public class GlobalConfig {

    /**
     * 配置
     */
    private static Map<String, String> configMap = new HashMap<>();

    /**
     * 设置系统参数
     *
     * @param key
     * @param value
     */
    public static void putConfig(String key, String value) {
        configMap.put(key, value);
    }

    /**
     * 得到系统参数
     *
     * @param key
     * @return
     */
    public static String getConfig(String key) {
        return getConfig(key, "");
    }

    /**
     * 得到系统参数
     *
     * @param key
     * @param def
     * @return
     */
    public static String getConfig(String key, String def) {
        if (configMap.containsKey(key)) {
            return configMap.get(key);
        }
        return def;
    }

    /**
     * 得到系统参数
     *
     * @param key
     * @return
     */
    public static Integer getInt(String key) {
        return getInt(key, 0);
    }

    /**
     * 得到系统参数
     *
     * @param key
     * @param def
     * @return
     */
    public static Integer getInt(String key, Integer def) {
        Integer value = def;
        if (configMap.containsKey(key)) {
            String str = configMap.get(key);
            try {
                value = Integer.parseInt(str);
            } catch (Exception ignored) {
            }
        }
        return value;
    }

    /**
     * 得到系统参数
     *
     * @param key
     * @param def
     * @return
     */
    public static Double getDouble(String key, Double def) {
        Double value = def;
        if (configMap.containsKey(key)) {
            String str = configMap.get(key);
            try {
                value = Double.parseDouble(str);
            } catch (Exception ignored) {
            }
        }
        return value;
    }

    /**
     * 得到系统参数
     *
     * @param key
     * @return
     */
    public static Double getDouble(String key) {
        return getDouble(key, 0D);
    }

}
