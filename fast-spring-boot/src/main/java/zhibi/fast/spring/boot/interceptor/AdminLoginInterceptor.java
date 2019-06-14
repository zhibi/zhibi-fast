package zhibi.fast.spring.boot.interceptor;

import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static zhibi.fast.commons.base.Constants.Session.LOGIN_ADMIN;

/**
 * 后台管理登录拦截器
 *
 * @author 执笔
 * @date 2019/4/11 18:08
 */
@Component
public class AdminLoginInterceptor extends HandlerInterceptorAdapter {

    /**
     * 管理员登录的url
     */
    @Setter
    private String adminLoginUrl = "/admin/login";
    /**
     * 登录管理员在session里面的标识
     */
    @Setter
    private String sessionAdmin = LOGIN_ADMIN;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object attribute = request.getSession().getAttribute(sessionAdmin);
        String contextPath = request.getContextPath();
        if (null == attribute) {
            response.sendRedirect(contextPath + adminLoginUrl);
            return false;
        }
        return super.preHandle(request, response, handler);
    }


}