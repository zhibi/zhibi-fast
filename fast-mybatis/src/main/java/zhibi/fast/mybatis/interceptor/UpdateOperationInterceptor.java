package zhibi.fast.mybatis.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import zhibi.fast.commons.base.Constants;
import zhibi.fast.mybatis.dto.BaseDomain;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Properties;

/**
 * 自动修改 操作人
 *
 * @author 执笔
 * @date 2019/4/11 18:21
 */
@Slf4j
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class UpdateOperationInterceptor implements Interceptor {

    @Resource
    private HttpSession session;

    public UpdateOperationInterceptor() {
        log.info("☆初始化☆ [mybatis 更新操作人插件]");
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Long            userId          = getLoginUserId();
        // 获取参数
        Object parameter = invocation.getArgs()[1];
        if (parameter != null) {
            // 获取所有的属性
            List<Field> fields = FieldUtils.getAllFieldsList(parameter.getClass());
            for (Field field : fields) {
                if ("operationId".equals(field.getName())) {
                    field.setAccessible(true);
                    field.set(parameter, userId);
                    log.debug("auto set field:{} value:{}", field.getName(), field.get(parameter));
                }
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 获取登录用户的ID
     *
     * @return
     */
    private Long getLoginUserId() {
        Object loginUser = session.getAttribute(Constants.Session.LOGIN_USER);
        if (loginUser == null) {
            loginUser = session.getAttribute(Constants.Session.LOGIN_ADMIN);
        }
        if (loginUser instanceof BaseDomain) {
            return ((BaseDomain) loginUser).getId();
        }
        return -1L;
    }
}
