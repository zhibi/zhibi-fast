package zhibi.fast.spring.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zhibi.fast.commons.utils.VerifyCodeUtils;
import zhibi.fast.spring.boot.annotation.Operation;
import zhibi.fast.spring.boot.service.VerifyCodeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * helper控制器
 *
 * @author 执笔
 */
@Controller
@RequestMapping("helper")
public class HelperController {

    @Autowired
    private VerifyCodeService verifyCodeService;


    /**
     * 图片验证码
     *
     * @param width  验证码宽度
     * @param height 验证码高度
     * @param size   验证码多少
     * @throws IOException
     */
    @Operation("获取验证码")
    @RequestMapping("verify")
    public void verify(HttpServletResponse response,
                       HttpServletRequest request,
                       String deviceId,
                       @RequestParam(defaultValue = "117") Integer width,
                       @RequestParam(defaultValue = "38") Integer height,
                       @RequestParam(defaultValue = "4") Integer size) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        String code = VerifyCodeUtils.outputVerifyImage(width, height, response.getOutputStream(), size);
        verifyCodeService.saveCode(request, code, deviceId);
    }

}
