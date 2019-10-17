package zhibi.fast.commons.base;

/**
 * 环境变量
 *
 * @author 执笔
 * @date 2019/4/11 14:49
 */
public interface Constants {

    /**
     * 返回消息标识
     */
    String MESSAGE = "message";


    /**
     * 错误返回页面标识
     */
    String BACK_RUL = "backRul";


    /**
     * 响应
     */
    interface Response {
        /**
         * 成功
         */
        String SUCCESS = "SUCCESS";
        /**
         * 错误
         */
        String ERROR   = "ERROR";

        /**
         * 失败
         */
        String FAIL = "FAIL";
    }

    /**
     * 日志MDC的标志
     */
    interface LogMdc {
        /**
         * 记录日志的标识<br/>
         * 日志文件中使用：[%9.9X{LOG_ID}]
         * <p>%d{HH:mm:ss} [${LOG_LEVEL_PATTERN:-%5p}] [%9.9X{LOG_ID}] [%-40.40logger{39} %5line{5}] : %msg%n</p>
         */
        String LOG_ID = "LOG_ID";

        /**
         * 记录日志的标识
         * 请求的ID
         */
        String REQUEST_ID = "REQUEST_ID";
    }

    /**
     * session 管理
     */
    interface Session {
        /**
         * 登录的用户
         */
        String LOGIN_USER  = "sessionUser";
        /**
         * 登录的管理员
         */
        String LOGIN_ADMIN = "sessionAdmin";

        /**
         * 验证码
         */
        String VERIFY_CODE = "sessionVerifyCode";

        /**
         * 登录的用户ID
         */
        String LOGIN_USER_ID = "sessionUserID";
    }

    /**
     * 参数
     */
    interface Param {
        /**
         * 响应参数
         */
        interface Response {
            /**
             * 相应码
             */
            String CODE = "code";

            /**
             * 响应消息
             */
            String MESSAGE = "message";

            /**
             * 响应内容
             */
            String RESPONSE  = "response";
            /**
             * 相应时间
             */
            String TIMESTAMP = "timestamp";
        }
    }

}
