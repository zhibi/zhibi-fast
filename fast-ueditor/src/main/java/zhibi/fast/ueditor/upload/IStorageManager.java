package zhibi.fast.ueditor.upload;

import zhibi.fast.ueditor.define.State;

import java.io.InputStream;

/**
 * @author 执笔
 * @date 2019/4/30 10:47
 */
public interface IStorageManager {


    State saveBinaryFile(byte[] data, String rootPath, String savePath, String urlPrefix);

    State saveFileByInputStream(InputStream is, String rootPath, String savePath, String urlPrefix);

    State saveFileByInputStream(InputStream is, String rootPath, String savePath, long maxSize, String urlPrefix);
}
