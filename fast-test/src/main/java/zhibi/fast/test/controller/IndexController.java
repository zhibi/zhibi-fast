package zhibi.fast.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import zhibi.fast.spring.boot.service.FileUploadService;

/**
 * @author 执笔
 * @date 2019/4/24 17:34
 */
@Controller
public class IndexController {

    @Autowired
    private FileUploadService fileUploadService;

    /**
     *
     */
    @ResponseBody
    @PostMapping("upload")
    public String upload(MultipartFile file) {
        String upload = fileUploadService.upload(file, "123456");
        return upload;
    }
}
