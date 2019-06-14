package zhibi.fast.commons.json;

import java.text.*;
import java.util.Date;

/**
 * 自定义 json 时间转换
 *
 * @author 执笔
 * @date 2019/4/23 12:08
 */
public class CustomerDateFormat extends DateFormat {

    private DateFormat dateFormat;

    private SimpleDateFormat simpleDateFormat;

    public CustomerDateFormat(DateFormat dateFormat, String format) {
        this.dateFormat = dateFormat;
        simpleDateFormat = new SimpleDateFormat(format);
    }

    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        return dateFormat.format(date, toAppendTo, fieldPosition);
    }

    @Override
    public Date parse(String source, ParsePosition pos) {
        Date date = null;
        try {
            date = simpleDateFormat.parse(source, pos);
        } catch (Exception e) {
            date = dateFormat.parse(source, pos);
        }
        return date;
    }

    /**
     * 主要还是装饰这个方法
     */
    @Override
    public Date parse(String source) throws ParseException {
        Date date = null;
        try {
            date = simpleDateFormat.parse(source);
        } catch (Exception e) {
            // 不行，那就按原先的规则吧
            date = dateFormat.parse(source);
        }
        return date;
    }

    @Override
    public Object clone() {
        return dateFormat.clone();
    }

}
