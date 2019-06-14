package zhibi.fast.ueditor.upload;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import zhibi.fast.ueditor.PathFormat;
import zhibi.fast.ueditor.define.AppInfo;
import zhibi.fast.ueditor.define.BaseState;
import zhibi.fast.ueditor.define.FileType;
import zhibi.fast.ueditor.define.State;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author 执笔
 * @date 2019/4/30 12:54
 */
public class BinaryUploader {

    private IStorageManager storage;

    public BinaryUploader(IStorageManager storage) {
        this.storage = storage;
    }

    private static boolean validType(String type, String[] allowTypes) {
        List<String> list = Arrays.asList(allowTypes);
        return list.contains(type);
    }

    public State save(HttpServletRequest request, Map<String, Object> conf) {
        if (request instanceof MultipartRequest) {
            return doSave((MultipartRequest) request, conf);
        } else {
            return doSave(request, conf);
        }
    }

    /**
     * springmvc 上传
     *
     * @param request
     * @param conf
     * @return
     */
    private State doSave(MultipartRequest request, Map<String, Object> conf) {
        Map<String, MultipartFile> map = request.getFileMap();
        try {
            MultipartFile file = null;
            for (MultipartFile temp : map.values()) {
                if (!temp.isEmpty()) {
                    file = temp;
                    break;
                }
            }
            if (file == null) {
                return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
            }
            return doSave(conf, file.getOriginalFilename(), file.getInputStream());
        } catch (IOException e) {
            return new BaseState(false, AppInfo.IO_ERROR);
        }
    }

    /**
     * 保存文件
     *
     * @param conf           配置
     * @param originFileName 文件原名
     * @param inputStream
     * @return
     * @throws IOException
     */
    private State doSave(Map<String, Object> conf, String originFileName, InputStream inputStream) throws IOException {
        String savePath  = (String) conf.get("savePath");
        String urlPrefix = (String) conf.get("urlPrefix");
        String suffix    = FileType.getSuffixByFilename(originFileName);
        originFileName = originFileName.substring(0, originFileName.length() - suffix.length());
        savePath = savePath + suffix;
        long maxSize = (Long) conf.get("maxSize");
        if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
            return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
        }
        savePath = PathFormat.parse(savePath, originFileName);
        String rootPath     = (String) conf.get("rootPath");
        State  storageState = storage.saveFileByInputStream(inputStream, rootPath, savePath, maxSize, urlPrefix);
        inputStream.close();
        if (storageState.isSuccess()) {
            storageState.putInfo("type", suffix);
            storageState.putInfo("original", originFileName + suffix);
        }
        return storageState;
    }

    /**
     * 普通 request 上传
     *
     * @param request
     * @param conf
     * @return
     */
    private State doSave(HttpServletRequest request, Map<String, Object> conf) {
        FileItemStream fileStream   = null;
        boolean        isAjaxUpload = request.getHeader("X_Requested_With") != null;
        if (!ServletFileUpload.isMultipartContent(request)) {
            return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
        }
        ServletFileUpload upload = new ServletFileUpload(
                new DiskFileItemFactory());
        if (isAjaxUpload) {
            upload.setHeaderEncoding("UTF-8");
        }
        try {
            FileItemIterator iterator = upload.getItemIterator(request);
            while (iterator.hasNext()) {
                fileStream = iterator.next();
                if (!fileStream.isFormField()) {
                    break;
                }
                fileStream = null;
            }
            if (fileStream == null) {
                return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
            }
            return doSave(conf, fileStream.getName(), fileStream.openStream());
        } catch (FileUploadException e) {
            return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
        } catch (IOException e) {
        }
        return new BaseState(false, AppInfo.IO_ERROR);
    }


}
