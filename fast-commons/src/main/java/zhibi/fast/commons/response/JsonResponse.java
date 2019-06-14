package zhibi.fast.commons.response;

import lombok.Data;

import java.io.Serializable;

import static zhibi.fast.commons.base.Constants.Response.FAIL;
import static zhibi.fast.commons.base.Constants.Response.SUCCESS;

/**
 * @author 执笔
 * 返回JSON结果
 */
@Data
public class JsonResponse<T> implements Serializable {


    /**
     * 详细提示信息
     */
    private String message;
    /**
     * 相应码
     */
    private String code;
    /**
     * 响应内容
     */
    private T      response;
    /**
     * 响应时间
     */
    private Long   timestamp;

    private JsonResponse(String message, String code, T response) {
        this.message = message;
        this.code = code;
        this.response = response;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 请求成功
     *
     * @param info 提示信息
     * @param data 返回数据
     * @param <T>
     * @return
     */
    public static <T> JsonResponse<T> success(String info, T data) {
        return build(SUCCESS, info, data);
    }

    /**
     * 请求成功
     *
     * @param info 成功提示信息
     * @param <T>
     * @return
     */
    public static <T> JsonResponse<T> success(String info) {
        return build(SUCCESS, info, null);
    }

    /**
     * 请求成功
     *
     * @param data 返回的数据
     * @param <T>
     * @return
     */
    public static <T> JsonResponse<T> success(T data) {
        return build(SUCCESS, SUCCESS, data);
    }

    /**
     * 请求成功
     *
     * @param <T>
     * @return
     */
    public static <T> JsonResponse<T> success() {
        return build(SUCCESS, SUCCESS, null);
    }

    /**
     * 请求失败
     *
     * @param info 失败信息
     * @param <T>
     * @return
     */
    public static <T> JsonResponse<T> fail(String info) {
        return build(FAIL, info, null);
    }


    /**
     * 构建返回对象
     *
     * @param code
     * @param info
     * @param data
     * @param <T>
     * @return
     */
    public static <T> JsonResponse<T> build(String code, String info, T data) {
        return new JsonResponse<>(info, code, data);
    }

}
