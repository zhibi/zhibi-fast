package zhibi.fast.spring.boot.annotation;

import java.lang.annotation.*;

/**
 * @author 执笔
 * @date 2019/3/28 11:22
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Operation {

    /**
     * 操作
     */
    String value();

    /**
     * 不需要打印日志的参数
     */
    String[] ignoreParams() default {};

    /**
     * 是否需要记录日志
     *
     * @return
     */
    boolean needLog() default true;

    /**
     * 是否需要登录
     * @return
     */
    boolean needLogin() default false;
}
