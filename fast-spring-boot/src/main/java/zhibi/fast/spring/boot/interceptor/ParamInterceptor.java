package zhibi.fast.spring.boot.interceptor;

import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 请求参数拦截器<br/>
 * 主要是方便参数回显
 *
 * @author 执笔
 * @date 2019/4/11 16:50
 */
@Component
public class ParamInterceptor extends HandlerInterceptorAdapter {

    @Setter
    private List<String> ignoreParams = new ArrayList<String>() {{
        add("password");
    }};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String string = enumeration.nextElement();
            if (!ignoreParams.contains(string)) {
                request.setAttribute(string, request.getParameter(string));
            }
        }
        return super.preHandle(request, response, handler);
    }
}
