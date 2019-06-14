package zhibi.fast.spring.boot.interceptor;

import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import zhibi.fast.spring.boot.annotation.Operation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static zhibi.fast.commons.base.Constants.Session.LOGIN_USER;

/**
 * 登陆请求拦截器
 *
 * @author 执笔
 * @date 2019/4/2 19:17
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 登录用户在session里面的标识
     */
    @Setter
    private String sessionUser = LOGIN_USER;

    /**
     * 登录的url
     */
    @Setter
    private String loginUrl = "login";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object attribute = request.getSession().getAttribute(sessionUser);
        String contextPath = request.getContextPath();
        Operation operation = null;
        // 得到注解
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            boolean flag = method.getBeanType().isAnnotationPresent(Operation.class);
            if (!flag) {
                flag = method.hasMethodAnnotation(Operation.class);
                if (flag) {
                    operation = method.getMethodAnnotation(Operation.class);
                }
            } else {
                operation = method.getBeanType().getAnnotation(Operation.class);
            }
        }
        if (null != operation && operation.needLogin() && null == attribute) {
            response.sendRedirect(contextPath + "/" + loginUrl);
            return false;
        }
        return true;
    }
}
