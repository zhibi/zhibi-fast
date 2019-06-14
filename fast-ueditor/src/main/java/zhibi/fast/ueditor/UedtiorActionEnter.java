package zhibi.fast.ueditor;

import zhibi.fast.ueditor.define.ActionMap;
import zhibi.fast.ueditor.define.AppInfo;
import zhibi.fast.ueditor.define.BaseState;
import zhibi.fast.ueditor.define.State;
import zhibi.fast.ueditor.hunter.FileManager;
import zhibi.fast.ueditor.hunter.ImageHunter;
import zhibi.fast.ueditor.upload.Base64Uploader;
import zhibi.fast.ueditor.upload.BinaryUploader;
import zhibi.fast.ueditor.upload.IStorageManager;
import zhibi.fast.ueditor.upload.StorageManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 自定义 ueditor 上传文件处理器
 *
 * @author 执笔
 * @date 2019/4/30 10:28
 */
public class UedtiorActionEnter {

    private ConfigManager configManager = null;

    private IStorageManager storage;

    public UedtiorActionEnter(String rootPath) {
        this(new StorageManager(), rootPath, null, null);
    }

    public UedtiorActionEnter(String rootPath, String configPath) {
        this(new StorageManager(), rootPath, configPath, null);
    }

    public UedtiorActionEnter(String rootPath, String configPath, String urlPrefix) {
        this(new StorageManager(), rootPath, configPath, urlPrefix);
    }

    public UedtiorActionEnter(IStorageManager storage, String rootPath) {
        this(storage, rootPath, null, null);
    }

    public UedtiorActionEnter(IStorageManager storage, String rootPath, String configPath, String urlPrefix) {
        this.storage = storage;
        this.configManager = ConfigManager.getInstance(rootPath, configPath, urlPrefix);
    }

    /**
     * 执行具体操作
     *
     * @return
     */
    public String exec(HttpServletRequest request) {
        String callbackName = request.getParameter("callback");
        if (callbackName != null) {
            if (!validCallbackName(callbackName)) {
                return new BaseState(false, AppInfo.ILLEGAL).toJSONString();
            }
            return callbackName + "(" + this.invoke(request) + ");";
        } else {
            return this.invoke(request);
        }
    }


    /**
     * 实际执行
     *
     * @return
     */
    private String invoke(HttpServletRequest request) {
        String actionType = request.getParameter("action");
        if (actionType == null || !ActionMap.mapping.containsKey(actionType)) {
            return new BaseState(false, AppInfo.INVALID_ACTION).toJSONString();
        }
        if (this.configManager == null || !this.configManager.valid()) {
            return new BaseState(false, AppInfo.CONFIG_ERROR).toJSONString();
        }
        State               state      = null;
        int                 actionCode = ActionMap.getType(actionType);
        Map<String, Object> conf       = null;
        switch (actionCode) {
            case ActionMap.CONFIG:
                return this.configManager.getAllConfig().toString();
            case ActionMap.UPLOAD_IMAGE:
            case ActionMap.UPLOAD_SCRAWL:
            case ActionMap.UPLOAD_VIDEO:
            case ActionMap.UPLOAD_FILE: {
                conf = this.configManager.getConfig(actionCode);
                String filedName = (String) conf.get("fieldName");
                if ("true".equals(conf.get("isBase64"))) {
                    state = new Base64Uploader(storage).save(request.getParameter(filedName), conf);
                } else {
                    state = new BinaryUploader(storage).save(request, conf);
                }
                break;
            }
            case ActionMap.CATCH_IMAGE:
                conf = configManager.getConfig(actionCode);
                String[] list = request.getParameterValues((String) conf.get("fieldName"));
                state = new ImageHunter(storage, conf).capture(list);
                break;

            case ActionMap.LIST_IMAGE:
            case ActionMap.LIST_FILE:
                conf = configManager.getConfig(actionCode);
                int start = this.getStartIndex(request);
                state = new FileManager(conf).listFile(start);
                break;
            default:
                break;
        }
        return state.toJSONString();
    }

    private int getStartIndex(HttpServletRequest request) {
        String start = request.getParameter("start");
        try {
            return Integer.parseInt(start);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * callback参数验证
     */
    private boolean validCallbackName(String name) {
        return name.matches("^[a-zA-Z_]+[\\w0-9_]*$");
    }

}
