package zhibi.fast.spring.boot.freemarker;

import freemarker.template.*;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * freemarker 添加 java8 时间类型转换
 *
 * @author 执笔
 * @date 2019/4/20 22:41
 */
public class CustomObjectWrapper extends DefaultObjectWrapper {
    public CustomObjectWrapper(Version incompatibleImprovements) {
        super(incompatibleImprovements);
    }

    @Override
    public TemplateModel wrap(Object obj) throws TemplateModelException {
        if (obj instanceof LocalDateTime) {
            Timestamp timestamp = Timestamp.valueOf((LocalDateTime) obj);
            return new SimpleDate(timestamp);
        }
        if (obj instanceof LocalDate) {
            Date date = Date.valueOf((LocalDate) obj);
            return new SimpleDate(date);
        }
        if (obj instanceof LocalTime) {
            Time time = Time.valueOf((LocalTime) obj);
            return new SimpleDate(time);
        }
        return super.wrap(obj);
    }
}
