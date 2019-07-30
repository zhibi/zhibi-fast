package zhibi.fast.poi.excel.export.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片文件加载
 *
 * @author 执笔
 * @date 2018/11/26 15:34
 */
@Slf4j
public class ImageFileLoader {

    /**
     * 简单缓存
     */
    private Map<String, byte[]> cache = new HashMap<>();

    /**
     * 加载图片数据
     *
     * @param filePath
     * @return
     */
    public byte[] loadData(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return null;
        }
        if (cache.containsKey(filePath)) {
            return cache.get(filePath);
        } else {
            byte[] bytes = null;
            try {
                /**
                 * 网络图片
                 */
                if (filePath.startsWith("http")) {
                    bytes = IOUtils.toByteArray(new URI(filePath));
                }
                if (bytes == null) {
                    /**
                     * 相对路径
                     */
                    try {
                        bytes = IOUtils.toByteArray(new FileInputStream(filePath));
                    } catch (IOException ignored) {
                        /**
                         * 相对路径
                         */
                        bytes = IOUtils.toByteArray(this.getClass().getClassLoader().getResourceAsStream(filePath));
                    }
                }
            } catch (Exception ignored) {
            }
            if (bytes == null) {
                log.error("file not found ：{}", filePath);
            }
            cache.put(filePath, bytes);
            return bytes;
        }
    }

    /**
     * 得到 图片类型
     * <p>
     * PICTURE_TYPE_EMF = 2;
     * PICTURE_TYPE_WMF = 3;
     * PICTURE_TYPE_PICT = 4;
     * PICTURE_TYPE_JPEG = 5;
     * PICTURE_TYPE_PNG = 6;
     * PICTURE_TYPE_DIB = 7;
     *
     * @param photoByte
     * @return
     */
    public int getFileExtendName(byte[] photoByte) {
        int strFileExtendName = 5;
        if ((photoByte[0] == 66) && (photoByte[1] == 77)) {
            strFileExtendName = 2;
        } else if ((photoByte[1] == 80) && (photoByte[2] == 78) && (photoByte[3] == 71)) {
            strFileExtendName = 6;
        }
        return strFileExtendName;
    }
}
