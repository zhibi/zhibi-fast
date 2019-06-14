package zhibi.fast.spring.boot.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author 执笔
 * @date 2019/5/10 13:06
 */
@ControllerAdvice
@Slf4j
public class FileUploadExceptionHandler extends BaseExceptionHandler {

    /**
     * 最大文件限制
     */
    @Value("${spring.servlet.multipart.max-file-size:1M}")
    private String maxFileSize;

    /**
     * 最大文件限制异常
     *
     * @param request
     * @param e
     * @param response
     * @return
     */
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public Object maxUploadSizeExceptionHandler(HttpServletRequest request, MaxUploadSizeExceededException e, HttpServletResponse response) {
        String message = "上传的文件不能超过：" + maxFileSize;
        return error(response, request, message);
    }
}
