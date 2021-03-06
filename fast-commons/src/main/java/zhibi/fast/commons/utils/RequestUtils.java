package zhibi.fast.commons.utils;


import org.apache.commons.io.IOUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author Administrator
 */
public class RequestUtils {

    /**
     * 判断是不是微信浏览器
     *
     * @param request
     * @return
     */
    public static boolean isWeChat(HttpServletRequest request) {
        if (request == null || request.getHeader("user-agent") == null) {
            return false;
        }
        String ua = request.getHeader("user-agent").toLowerCase();
        return ua.indexOf("micromessenger") > 0;
    }

    /**
     * 读取request内容
     *
     * @return
     */
    public static String readInputStream(HttpServletRequest request) {
        StringWriter writer = new StringWriter();
        try {
            ServletInputStream inputStream = request.getInputStream();
            IOUtils.copy(inputStream, writer, StandardCharsets.UTF_8);
        } catch (IOException ignored) {
        }
        return writer.toString();
    }

    /**
     * 构建form表单
     *
     * @param url   提交地址
     * @param param 提交参数
     * @return
     */
    public static String buildForm(String url, Map<String, String> param) {
        StringBuffer sbHtml = new StringBuffer();
        sbHtml.append("正在跳转。。。<br/>")
                .append("<form id='form' name='form' ")
                .append(" action='").append(url).append("'")
                .append(" method = 'post' style ='display:none' >");
        param.forEach((k, v) -> sbHtml.append("<input type='text' name='")
                .append(k).append("' value='").append(v).append("'/><br/>"));
        sbHtml.append("</form>");
        sbHtml.append("<script>document.forms['form'].submit();</script>");
        return sbHtml.toString();
    }

    /**
     * Map转字符串
     * <p>aa=1&bb=3
     * 自动根据字母排序
     *
     * @param map      参数
     * @param encoding 编码
     * @return
     */
    public static String map2str(Map<String, String> map, String encoding) throws UnsupportedEncodingException {
        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            sb.append(key).append("=");
            if (encoding != null) {
                String value = map.get(key);
                if (value != null) {
                    sb.append(URLEncoder.encode(value, encoding));
                }
            } else {
                sb.append(map.get(key));
            }
            sb.append("&");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 把参数转换为字符串
     *
     * @param request
     * @return String
     * @author 执笔
     * @date 2016年1月7日 下午10:22:06
     */
    public static String request2String(HttpServletRequest request) {
        StringBuilder buffer = new StringBuilder();
        Enumeration<String> enumeration = request.getParameterNames();
        boolean isFirst = true;
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            if (!"password".equals(key) && !"pageNum".equals(key)) {
                if (isFirst) {
                    buffer.append(key).append("=").append(request.getParameter(key));
                    isFirst = false;
                } else {
                    buffer.append("&").append(key).append("=").append(request.getParameter(key));
                }
            }
        }
        return buffer.toString();
    }
}
