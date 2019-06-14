package zhibi.fast.ueditor.define;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author 执笔
 * @date 2019/4/30 10:42
 */
public class MIMEType {

    public static final Map<String, String> TYPES = new HashMap<String, String>();
    static {
        TYPES.put("image/gif", ".gif");
        TYPES.put("image/jpeg", ".jpg");
        TYPES.put("image/jpg", ".jpg");
        TYPES.put("image/png", ".png");
        TYPES.put("image/bmp", ".bmp");
    }

    public static String getSuffix ( String mime ) {
        return MIMEType.TYPES.get( mime );
    }

}
