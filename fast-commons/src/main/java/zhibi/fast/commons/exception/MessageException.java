package zhibi.fast.commons.exception;

import lombok.Data;

/**
 * 自定义异常
 *
 * @author 执笔
 */
@Data
public class MessageException extends RuntimeException {

    /**
     * 错误码
     */
    private String code = "FAIL";

    /**
     * 返回地址
     */
    private String backUrl;

    public MessageException(String msg) {
        super(msg);
    }

    public MessageException(Throwable throwable) {
        super(throwable);
    }

    public MessageException(String message, String code, String backUrl) {
        super(message);
        this.code = code;
        this.backUrl = backUrl;
    }

    public MessageException(String message, String code) {
        super(message);
        this.code = code;
    }


}
