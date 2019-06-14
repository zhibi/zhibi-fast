package zhibi.fast.generate.enums;

/**
 * 命名策略
 * @author 执笔
 * @date 2019/4/18 21:07
 */
public enum NamingStrategy {

    /**
     * 不做任何改变，原样输出
     */
    NOCHANGE,

    /**
     * 下划线和驼峰相互转换
     * sql里面使用下划线
     * 类里面使用驼峰
     */
    UNDERLINE_MUTUAL_CAMEL,

}
