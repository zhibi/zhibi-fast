package zhibi.fast.spring.boot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zhibi.fast.spring.boot.properties.UploadProperties;
import zhibi.fast.ueditor.UedtiorActionEnter;

import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * UEditor富文本编辑器
 *
 * @author 执笔
 * @date 2019/4/3 16:55
 */
@RequestMapping("ueditor")
@RestController
@Slf4j
@ConditionalOnClass(UedtiorActionEnter.class)
public class UeditorController {

    private UedtiorActionEnter actionEnter;
    @Autowired
    private UploadProperties   uploadProperties;

    @PostConstruct
    public void init2() {
        String uploadPath = uploadProperties.getPath();
        String prefix     = uploadProperties.getPrefix();
        this.actionEnter = new UedtiorActionEnter(uploadPath, null, prefix);
    }


    /**
     * 服务统一管理
     *
     * @return
     */
    @RequestMapping(value = "server")
    public void server(HttpServletRequest request, HttpServletResponse response,
                       @RequestParam(defaultValue = "") String action) throws IOException {
        String uploadPath = uploadProperties.getPath();
        String result     = actionEnter.exec(request);
        //在下面判断如果是列出文件或图片的，替换物理路径的“/” 不然图片管理会有问题
        if ("listfile".equals(action) || "listimage".equals(action)) {
            uploadPath = uploadPath.replace("\\", "/");
            result = result.replaceAll(uploadPath, "/");
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        ServletOutputStream outputStream = response.getOutputStream();
        if ("config".equals(action) || "listfile".equals(action) || "listimage".equals(action)) {
            response.setContentType("application/x-javascript");
            outputStream.write(result.getBytes());
        } else {
            response.setContentType("text/html; charset=utf-8");
            outputStream.write(result.getBytes());
        }
    }
}
