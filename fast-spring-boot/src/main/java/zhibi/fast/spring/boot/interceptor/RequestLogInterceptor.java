package zhibi.fast.spring.boot.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import zhibi.fast.commons.utils.LogMdcUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.regex.Pattern;

import static zhibi.fast.commons.base.Constants.LogMdc.LOG_ID;
import static zhibi.fast.commons.base.Constants.LogMdc.REQUEST_ID;

/**
 * 请求日志拦截器<br/>
 * 主要是为了记录请求的详细日志
 *
 * @author 执笔
 * @date 2019/4/11 17:00
 */
@Component
@Slf4j
public class RequestLogInterceptor extends HandlerInterceptorAdapter {

    private static Pattern PATTERN = Pattern.compile("[ \t\n\r]");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        LogMdcUtils.putNotExist(REQUEST_ID, RandomStringUtils.randomAlphanumeric(9));
        log.info("★请求★ {} ", buildAccessLog(request));
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LogMdcUtils.removeExist(LOG_ID);
        super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 请求需要打印的日志
     *
     * @param request
     * @return
     */
    private String buildAccessLog(HttpServletRequest request) {
        StringBuilder accessLog = new StringBuilder();
        build(accessLog, request.getRemoteHost());
        build(accessLog, request.getMethod());
        build(accessLog, buildRequestPath(request));
        build(accessLog, buildRequestParam(request));
        build(accessLog, buildRequestHeard(request));
        return accessLog.toString();
    }

    /**
     * 拼接字符串
     *
     * @param sb
     * @param str
     */
    private void build(StringBuilder sb, String str) {
        sb.append(" ");
        if (StringUtils.isEmpty(str)) {
            sb.append("-");
        } else if (PATTERN.matcher(str).find()) {
            sb.append("\"");
            sb.append(str.replace("\"", "\"\""));
            sb.append("\"");
        } else {
            sb.append(str);
        }
    }

    /**
     * 请求路径
     *
     * @return
     */
    private String buildRequestPath(HttpServletRequest request) {
        return request.getRequestURI();
    }

    /**
     * 请求参数
     *
     * @param request
     * @return
     */
    private String buildRequestParam(HttpServletRequest request) {
        StringBuilder       buffer      = new StringBuilder("param:{");
        Enumeration<String> enumeration = request.getParameterNames();
        return getRequestString(request, buffer, enumeration);
    }

    /**
     * 请求头
     *
     * @param request
     * @return
     */
    private String buildRequestHeard(HttpServletRequest request) {
        StringBuilder       buffer      = new StringBuilder("header:{");
        Enumeration<String> enumeration = request.getHeaderNames();
        return getHeaderString(request, buffer, enumeration);
    }

    /**
     * 请求里面的数据
     *
     * @param request
     * @param buffer
     * @param enumeration
     * @return
     */
    private String getRequestString(HttpServletRequest request, StringBuilder buffer, Enumeration<String> enumeration) {
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            buffer.append(key).append(":").append(request.getParameter(key)).append(",");
        }
        buffer.append("}");
        return buffer.toString();
    }

    /**
     * 请求头
     *
     * @param request
     * @param buffer
     * @param enumeration
     * @return
     */
    private String getHeaderString(HttpServletRequest request, StringBuilder buffer, Enumeration<String> enumeration) {
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            buffer.append(key).append(":").append(request.getHeader(key)).append(",");
        }
        buffer.append("}");
        return buffer.toString();
    }

}
