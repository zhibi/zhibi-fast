package zhibi.fast.ueditor.upload;

import org.apache.commons.codec.binary.Base64;
import zhibi.fast.ueditor.PathFormat;
import zhibi.fast.ueditor.define.AppInfo;
import zhibi.fast.ueditor.define.BaseState;
import zhibi.fast.ueditor.define.FileType;
import zhibi.fast.ueditor.define.State;

import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author 执笔
 * @date 2019/4/30 10:48
 */
public final class Base64Uploader {

    private IStorageManager storage;

    public Base64Uploader(IStorageManager storage) {
        this.storage = storage;
    }

    private static byte[] decode(String content) {
        return Base64.decodeBase64(content.getBytes(UTF_8));
    }

    private static boolean validSize(byte[] data, long length) {
        return data.length <= length;
    }

    public State save(String content, Map<String, Object> conf) {
        byte[] data    = decode(content);
        long   maxSize = ((Long) conf.get("maxSize")).longValue();
        if (!validSize(data, maxSize)) {
            return new BaseState(false, AppInfo.MAX_SIZE);
        }
        String suffix = FileType.getSuffix("JPG");
        String savePath = PathFormat.parse((String) conf.get("savePath"),
                (String) conf.get("filename"));
        savePath = savePath + suffix;
        String rootPath     = (String) conf.get("rootPath");
        State  storageState = storage.saveBinaryFile(data, rootPath, savePath, conf.getOrDefault("", "").toString());
        if (storageState.isSuccess()) {
            storageState.putInfo("type", suffix);
            storageState.putInfo("original", "");
        }
        return storageState;
    }

}