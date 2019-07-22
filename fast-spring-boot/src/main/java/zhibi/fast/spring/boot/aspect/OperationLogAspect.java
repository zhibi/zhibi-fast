package zhibi.fast.spring.boot.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
import zhibi.fast.commons.utils.JSONUtils;
import zhibi.fast.commons.utils.LogMdcUtils;
import zhibi.fast.spring.boot.annotation.Operation;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

import static zhibi.fast.commons.base.Constants.LogMdc.LOG_ID;

/**
 * 操作日志记录切面
 *
 * @author 执笔
 * @date 2019/3/28 11:13
 */
@Aspect
@Slf4j
@Component
@ConditionalOnClass(ProceedingJoinPoint.class)
public class OperationLogAspect {

    public OperationLogAspect() {
        log.info("☆初始化☆ [操作日志切面]");
    }

    /**
     * 切面
     */
    @Pointcut("@annotation(zhibi.fast.spring.boot.annotation.Operation)")
    public void pointcut() {
    }

    /**
     * 通知
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = method.getName();
        Operation operation = method.getAnnotation(Operation.class);
        if (operation.needLog()) {
            StringBuffer paramBuffer = getParamLog(joinPoint, method, operation);
            LogMdcUtils.putNotExist(LOG_ID, RandomStringUtils.randomAlphanumeric(9));
            System.out.println();
            log.info("☆{}☆ 参数：{}  方法：{}.{}", operation.value(), paramBuffer, className, methodName);
            Object proceed = joinPoint.proceed();
            log.info("☆{}☆ 返回：{}", operation.value(), JSONUtils.toJson(proceed));
            System.out.println();
            LogMdcUtils.removeExist(LOG_ID);
            return proceed;
        } else {
            return joinPoint.proceed();
        }
    }

    /**
     * 得到请求参数日志
     * 忽略掉不需要打印的参数
     *
     * @param joinPoint
     * @param method
     * @param operation
     * @return
     */
    private StringBuffer getParamLog(ProceedingJoinPoint joinPoint, Method method, Operation operation) {
        List<String> ignoreParams = Arrays.asList(operation.ignoreParams());
        StringBuffer paramBuffer = new StringBuffer("{");
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            String name = parameters[i].getName();
            if (!ignoreParams.contains(name)) {
                paramBuffer
                        .append(name)
                        .append(":")
                        .append(JSONUtils.toJson(joinPoint.getArgs()[i]))
                        .append(",");
            }
        }
        return paramBuffer.append("}");
    }

}
