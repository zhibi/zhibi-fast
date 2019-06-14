package zhibi.fast.commons.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 字符串替换工具类
 *
 * @author 执笔
 * @date 2019/4/17 13:35
 */
public class ReplaceUtils {

    /**
     * 字符串脱敏
     * 除去开始和结束的 中间用*脱敏
     *
     * @param source 源字符串
     * @param start  开始保留几位
     * @param end    后面保留几位
     * @return
     */
    public static String desensitization(String source, Integer start, Integer end) {
        if (StringUtils.isEmpty(source)) {
            return source;
        }
        if (source.length() <= (start + end)) {
            return source;
        }
        String regex = "(?<=\\w{" + start + "})\\w(?=\\w{" + end + "})";
        return source.replaceAll(regex, "*");
    }

    /**
     * 下划线转驼峰
     *
     * @param name
     * @return
     */
    public static String underlineToCamel(String name) {
        if (StringUtils.isBlank(name)) {
            return "";
        }
        String        tempName = name.toLowerCase();
        StringBuilder result   = new StringBuilder();
        // 用下划线将原始字符串分割
        String[] camels = tempName.split("_");
        // 跳过原始字符串中开头、结尾的下换线或双重下划线
        // 处理真正的驼峰片段
        Arrays.stream(camels).filter(camel -> !StringUtils.isEmpty(camel)).forEach(camel -> {
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel);
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(capitalFirst(camel));
            }
        });
        return result.toString();
    }

    /**
     * 驼峰转下划线
     *
     * @param name
     * @return
     */
    public static String camelToUnderline(String name) {
        if (StringUtils.isBlank(name)) {
            return "";
        }
        int len = name.length();
        //只有一个字母不转换
        if (len < 2) {
            return name;
        }
        StringBuilder sb = new StringBuilder(len);
        //忽略掉首字母大写  和 末尾大写的字母
        sb.append(Character.toLowerCase(name.charAt(0)));
        for (int i = 1; i < len - 1; i++) {
            char c = name.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("_");
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        sb.append(Character.toLowerCase(name.charAt(len - 1)));
        return sb.toString();
    }

    /**
     * <p>
     * 首字母大写
     * </p>
     *
     * @param name 待转换的字符串
     * @return 转换后的字符串
     */
    public static String capitalFirst(String name) {
        if (!StringUtils.isEmpty(name)) {
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        return "";
    }

}
