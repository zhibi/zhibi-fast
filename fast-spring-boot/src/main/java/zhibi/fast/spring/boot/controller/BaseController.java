package zhibi.fast.spring.boot.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import zhibi.fast.commons.base.Constants;
import zhibi.fast.commons.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * controller基础实现类
 *
 * @author 执笔
 */
@Component
@Slf4j
public abstract class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpSession session;

    /**
     * 将数据放在model里面
     *
     * @param model
     * @param attributes
     * @Date 2016年8月23日下午2:53:52
     */
    protected void setModelAttribute(Model model, Object... attributes) {
        if (attributes != null && attributes.length > 0) {
            for (Object object : attributes) {
                if (null != object) {
                    model.addAttribute(object);
                }
            }
        }
        // 搜索的时候使用
        model.addAttribute("requestUrl", request.getRequestURI() + "?" + RequestUtils.request2String(request));
    }

    /**
     * 刷新页面
     * rest模式不能使用
     *
     * @return
     */
    protected String refresh() {
        return "redirect:" + request.getHeader("Referer");
    }


    /**
     * 重定向
     * rest模式不能使用
     *
     * @param viewName 重定向地址
     * @return
     */
    protected String redirect(String viewName) {
        return "redirect:" + viewName;
    }


    /**
     * 重定向并提示
     * rest模式不能使用
     *
     * @param viewName 重定向地址
     * @param message  重定向提示
     * @return
     */
    protected String redirect(String viewName, String message, RedirectAttributes attributes) {
        attributes.addFlashAttribute(Constants.MESSAGE, message);
        attributes.addFlashAttribute(Constants.Param.Response.CODE, Constants.Response.SUCCESS);
        return "redirect:" + viewName;
    }

    /**
     * 刷新并提示
     * rest模式不能使用
     *
     * @param message 提示消息
     * @return
     */
    protected String refresh(String message, RedirectAttributes attributes) {
        attributes.addFlashAttribute(Constants.MESSAGE, message);
        attributes.addFlashAttribute(Constants.Param.Response.CODE, Constants.Response.SUCCESS);
        return "redirect:" + request.getHeader("Referer");
    }

    /**
     * 重定向并提示操作失败
     * rest模式不能使用
     *
     * @param viewName 重定向地址
     * @param message  重定向提示
     * @return
     */
    protected String redirectError(String viewName, String message, RedirectAttributes attributes) {
        attributes.addFlashAttribute(Constants.MESSAGE, message);
        attributes.addFlashAttribute(Constants.Param.Response.CODE, Constants.Response.ERROR);
        return "redirect:" + viewName;
    }

    /**
     * 刷新并提示操作失败
     * rest模式不能使用
     *
     * @param message 提示消息
     * @return
     */
    protected String refreshError(String message, RedirectAttributes attributes) {
        attributes.addFlashAttribute(Constants.MESSAGE, message);
        attributes.addFlashAttribute(Constants.Param.Response.CODE, Constants.Response.ERROR);
        return "redirect:" + request.getHeader("Referer");
    }

    /**
     * 获取登录的用户ID
     *
     * @return
     */
    protected Long loginUserId() {
        Object userId = session.getAttribute(Constants.Session.LOGIN_USER_ID);
        try {
            return Long.parseLong(userId.toString());
        } catch (Exception e) {
            return 0L;
        }
    }

    /**
     * 获取登录的用户ID
     *
     * @return
     */
    protected void loginUserId(Long userId) {
        session.setAttribute(Constants.Session.LOGIN_USER_ID, userId);
    }

}
