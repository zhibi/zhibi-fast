package zhibi.fast.ueditor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;
import zhibi.fast.ueditor.define.ActionMap;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


/**
 * 配置管理器
 *
 * @author hancong03@baidu.com
 */
public final class ConfigManager {

    private static final String     CONFIG_DEFAULT   = "config-default.json";
    // 涂鸦上传filename定义
    private final static String     SCRAWL_FILE_NAME = "scrawl";
    // 远程图片抓取filename定义
    private final static String     REMOTE_FILE_NAME = "remote";
    private final        String     rootPath;
    private final        String     urlPrefix;
    private final        String     configPath;
    private              JSONObject jsonConfig       = null;

    /**
     * 通过一个给定的路径构建一个配置管理器
     */
    private ConfigManager(String rootPath, String configPath, String urlPrefix)
            throws IOException {
        rootPath = rootPath.replace("\\", "/");
        this.rootPath = rootPath;
        this.urlPrefix = urlPrefix;
        this.configPath = configPath;
        this.initEnv();
    }

    /**
     * 配置管理器构造工厂
     *
     * @param urlPrefix   url 前缀
     * @param rootPath    上传文件根路径
     * @param configPath  配置文件所在地址
     * @return 配置管理器实例或者null
     */
    public static ConfigManager getInstance(String rootPath, String configPath, String urlPrefix) {
        try {
            return new ConfigManager(rootPath, configPath, urlPrefix);
        } catch (Exception e) {
            return null;
        }
    }

    // 验证配置文件加载是否正确
    public boolean valid() {
        return this.jsonConfig != null;
    }

    public JSONObject getAllConfig() {

        return this.jsonConfig;

    }

    public Map<String, Object> getConfig(int type) {
        Map<String, Object> conf     = new HashMap<String, Object>();
        String              savePath = null;
        switch (type) {
            case ActionMap.UPLOAD_FILE:
                conf.put("isBase64", "false");
                conf.put("maxSize", this.jsonConfig.getLong("fileMaxSize"));
                conf.put("allowFiles", this.getArray("fileAllowFiles"));
                conf.put("fieldName", this.jsonConfig.getString("fileFieldName"));
                savePath = this.jsonConfig.getString("filePathFormat");
                break;
            case ActionMap.UPLOAD_IMAGE:
                conf.put("isBase64", "false");
                conf.put("maxSize", this.jsonConfig.getLong("imageMaxSize"));
                conf.put("allowFiles", this.getArray("imageAllowFiles"));
                conf.put("fieldName", this.jsonConfig.getString("imageFieldName"));
                savePath = this.jsonConfig.getString("imagePathFormat");
                break;

            case ActionMap.UPLOAD_VIDEO:
                conf.put("maxSize", this.jsonConfig.getLong("videoMaxSize"));
                conf.put("allowFiles", this.getArray("videoAllowFiles"));
                conf.put("fieldName", this.jsonConfig.getString("videoFieldName"));
                savePath = this.jsonConfig.getString("videoPathFormat");
                break;

            case ActionMap.UPLOAD_SCRAWL:
                conf.put("filename", ConfigManager.SCRAWL_FILE_NAME);
                conf.put("maxSize", this.jsonConfig.getLong("scrawlMaxSize"));
                conf.put("fieldName", this.jsonConfig.getString("scrawlFieldName"));
                conf.put("isBase64", "true");
                savePath = this.jsonConfig.getString("scrawlPathFormat");
                break;

            case ActionMap.CATCH_IMAGE:
                conf.put("filename", ConfigManager.REMOTE_FILE_NAME);
                conf.put("filter", this.getArray("catcherLocalDomain"));
                conf.put("maxSize", this.jsonConfig.getLong("catcherMaxSize"));
                conf.put("allowFiles", this.getArray("catcherAllowFiles"));
                conf.put("fieldName", this.jsonConfig.getString("catcherFieldName") + "[]");
                savePath = this.jsonConfig.getString("catcherPathFormat");
                break;

            case ActionMap.LIST_IMAGE:
                conf.put("allowFiles", this.getArray("imageManagerAllowFiles"));
                conf.put("dir", this.jsonConfig.getString("imageManagerListPath"));
                conf.put("count", this.jsonConfig.getInteger("imageManagerListSize"));
                break;

            case ActionMap.LIST_FILE:
                conf.put("allowFiles", this.getArray("fileManagerAllowFiles"));
                conf.put("dir", this.jsonConfig.getString("fileManagerListPath"));
                conf.put("count", this.jsonConfig.getInteger("fileManagerListSize"));
                break;
            default:
                break;
        }
        conf.put("savePath", savePath);
        conf.put("rootPath", this.rootPath);
        conf.put("urlPrefix", this.urlPrefix == null ? "" : this.urlPrefix);
        return conf;
    }

    /**
     * 初始化环境
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void initEnv() throws FileNotFoundException, IOException {
        String configContent = this.readFile(this.configPath);
        try {
            this.jsonConfig = JSON.parseObject(configContent);
        } catch (Exception e) {
            this.jsonConfig = null;
        }
    }

    private String[] getArray(String key) {

        JSONArray jsonArray = this.jsonConfig.getJSONArray(key);
        String[]  result    = new String[jsonArray.size()];

        for (int i = 0, len = jsonArray.size(); i < len; i++) {
            result[i] = jsonArray.getString(i);
        }

        return result;

    }

    /**
     * 读取配置文件
     *
     * @param configPath
     * @return
     * @throws IOException
     */
    private String readFile(String configPath) throws IOException {
        StringBuilder builder = new StringBuilder();
        InputStream   inputStream;
        if (!StringUtils.isEmpty(configPath)) {
            try {
                inputStream = new FileInputStream(configPath);
            } catch (IOException e) {
                inputStream = this.getClass().getClassLoader().getResourceAsStream(configPath);
            }
        } else {
            inputStream = this.getClass().getClassLoader().getResourceAsStream(CONFIG_DEFAULT);
        }
        try {
            InputStreamReader reader     = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader    bfReader   = new BufferedReader(reader);
            String            tmpContent = null;
            while ((tmpContent = bfReader.readLine()) != null) {
                builder.append(tmpContent);
            }
            bfReader.close();
        } catch (UnsupportedEncodingException e) {
            // 忽略
        }
        return this.filter(builder.toString());
    }

    /**
     * 过滤输入字符串, 剔除多行注释以及替换掉反斜杠
     */
    private String filter(String input) {
        return input.replaceAll("/\\*[\\s\\S]*?\\*/", "");
    }

}
