package zhibi.fast.spring.boot.exception.handler;

import org.springframework.web.servlet.ModelAndView;
import zhibi.fast.commons.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static zhibi.fast.commons.base.Constants.BACK_RUL;
import static zhibi.fast.commons.base.Constants.Param.Response.*;

/**
 * 基础异常处理
 *
 * @author 执笔
 * @date 2019/5/10 13:40
 */
public abstract class BaseExceptionHandler {


    /**
     * 判断是否是ajax请求
     */
    protected static boolean isAjax(HttpServletRequest httpRequest) {
        return (httpRequest.getHeader("X-Requested-With") != null
                && "XMLHttpRequest".equals(httpRequest.getHeader("X-Requested-With")));
    }

    /**
     * 处理异常
     *
     * @param response
     * @param request
     * @param message
     * @return
     */
    protected Object error(HttpServletResponse response, HttpServletRequest request, String message) {
        if (isAjax(request)) {
            try {
                return errorAjax(response, request, message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return errorHtml(request, message);
        }
        return null;
    }

    /**
     * 处理html错误
     *
     * @param request
     * @param message
     * @return
     */
    protected ModelAndView errorHtml(HttpServletRequest request, String message) {
        Map<String, Object> map = getModel(request, message);
        return new ModelAndView("error", map);
    }

    /**
     * 处理ajax请求
     *
     * @param response
     * @param message
     * @return
     */
    protected Object errorAjax(HttpServletResponse response, HttpServletRequest request, String message) throws IOException {
        Map<String, Object> map = getModel(request, message);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        //具体操作
        writer.write(JSONUtils.toJson(map));
        writer.flush();
        writer.close();
        return null;
    }

    /**
     * 封装异常信息
     *
     * @param request
     * @param message
     * @return
     */
    private Map<String, Object> getModel(HttpServletRequest request, String message) {
        return new HashMap<String, Object>(4) {{
            put(MESSAGE, message);
            put(CODE, "FAIL");
            put(BACK_RUL, request.getHeader("Referer"));
            put(TIMESTAMP, System.currentTimeMillis());
        }};
    }

}
