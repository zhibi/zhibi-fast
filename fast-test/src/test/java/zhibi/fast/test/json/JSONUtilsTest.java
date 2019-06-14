package zhibi.fast.test.json;

import zhibi.fast.commons.utils.JSONUtils;

import java.util.Map;

/**
 * @author 执笔
 * @date 2019/4/23 13:17
 */
public class JSONUtilsTest {

    public static void main(String[] args) {
        String ss  = "{\"trustName\":555,\"accountName\":\"张贵红\",\"name\":{\"trustName\":555,\"accountName\":\"张贵红\"}}";
        System.out.println(JSONUtils.toMap(ss));
        System.out.println(JSONUtils.readObj(ss,"trustName",Integer.class));
        System.out.println(JSONUtils.readObj(ss,"accountName",String.class));
        System.out.println(JSONUtils.readObj(ss,"name", Map.class));
    }
}
