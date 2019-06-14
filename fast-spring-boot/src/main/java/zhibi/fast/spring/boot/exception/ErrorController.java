package zhibi.fast.spring.boot.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;
import zhibi.fast.commons.exception.MessageException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static zhibi.fast.commons.base.Constants.BACK_RUL;
import static zhibi.fast.commons.base.Constants.Param.Response.*;

/**
 * 全局统一异常处理
 *
 * @author 执笔
 */
@Controller
@Slf4j
public class ErrorController extends BasicErrorController {

    private ErrorAttributes errorAttributes;

    public ErrorController(ServerProperties serverProperties) {
        super(new DefaultErrorAttributes(), serverProperties.getError());
        errorAttributes = new DefaultErrorAttributes();
    }

    /**
     * 覆盖默认的Json响应
     */
    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> map = resolveErrorModel(request);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * 覆盖默认的HTML响应
     */
    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map          = resolveErrorModel(request);
        ModelAndView        modelAndView = resolveErrorView(request, response, getStatus(request), map);
        return (modelAndView != null) ? modelAndView : new ModelAndView("error", map);
    }

    /**
     * 解析错误消息
     *
     * @param request
     * @return
     */
    private Map<String, Object> resolveErrorModel(HttpServletRequest request) {
        Throwable           error = errorAttributes.getError(new ServletWebRequest(request));
        Map<String, Object> model = getErrorAttributes(request, true);
        Map<String, Object> map = new HashMap<String, Object>(4) {{
            put(MESSAGE, model.get(MESSAGE));
            put(CODE, "FAIL");
            put(BACK_RUL, request.getHeader("Referer"));
            put(TIMESTAMP, System.currentTimeMillis());
        }};
        if (error instanceof MessageException) {
            if (StringUtils.isNoneBlank(((MessageException) error).getBackUrl())) {
                map.put(BACK_RUL, ((MessageException) error).getBackUrl());
            }
            map.put(CODE, ((MessageException) error).getCode());
        }
        log.error("【ERROR】 " + model);
        return map;
    }
}
