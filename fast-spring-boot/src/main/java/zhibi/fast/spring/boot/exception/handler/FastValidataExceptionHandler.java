package zhibi.fast.spring.boot.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * 校验异常管理
 *
 * @author 执笔
 * @date 2018/12/26 13:54
 */
@ConditionalOnClass({BindException.class, ConstraintViolationException.class})
@ControllerAdvice
@Slf4j
public class FastValidataExceptionHandler extends BaseExceptionHandler{

    /**
     * 校验异常处理
     *
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public Object bindExceptionHandler(HttpServletRequest request, BindException e, HttpServletResponse response) {
        StringBuilder builder = new StringBuilder();
        List<ObjectError> allErrors = e.getAllErrors();
        allErrors.forEach(error -> builder.append(error.getDefaultMessage()));
        return error(response, request, builder.toString());
    }

    /**
     * 参数校验异常处理
     *
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public Object constraintViolationExceptionHandler(HttpServletRequest request, ConstraintViolationException e, HttpServletResponse response) {
        StringBuilder builder = new StringBuilder();
        e.getConstraintViolations().forEach(item -> builder.append(item.getMessage()));
        return error(response, request, builder.toString());
    }
}
