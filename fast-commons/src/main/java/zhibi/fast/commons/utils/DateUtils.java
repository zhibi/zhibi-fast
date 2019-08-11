package zhibi.fast.commons.utils;

import java.text.ParseException;
import java.util.Date;

/**
 * @author 执笔
 * @date 2019/8/11 17:05
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {


    /**
     * 两个日期相差的天数
     * @param before
     * @param after
     * @return
     */
    public static long differentDays(Date before, Date after){
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }

}
