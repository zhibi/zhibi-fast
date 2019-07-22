package zhibi.fast.spring.boot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import zhibi.fast.spring.boot.properties.UploadProperties;
import zhibi.fast.spring.boot.service.FileUploadService;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * 本地文件上传
 *
 * @author 执笔
 * @date 2019/4/12 15:30
 */
@Service
@Slf4j
public class LocalFileUploadServiceImpl implements FileUploadService {

    @Autowired
    private UploadProperties uploadProperties;

    @Override
    public String upload(MultipartFile file, String model) {
        String uploadPath = uploadProperties.getPath();
        String prefix     = uploadProperties.getPrefix();
        String path       = "/" + model + "/" + DateFormatUtils.format(new Date(), "yyyy-MM-dd") + "/";
        String fileName   = generateNewName(file.getOriginalFilename());
        File   newFile    = new File(uploadPath + path + fileName);
        newFile.getParentFile().mkdirs();
        try {
            newFile.createNewFile();
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("◎Upload File◎  " + newFile.getAbsolutePath());
        return prefix + path + fileName;
    }

    @Override
    public String upload(File file, @NotBlank String model) {
        String uploadPath = uploadProperties.getPath();
        String prefix     = uploadProperties.getPrefix();
        String path       = "/" + model + "/" + DateFormatUtils.format(new Date(), "yyyy-MM-dd") + "/";
        String fileName   = generateNewName(file.getName());
        File   newFile    = new File(uploadPath + path + fileName);
        newFile.getParentFile().mkdirs();
        try {
            newFile.createNewFile();
            IOUtils.copy(new FileInputStream(file), new FileOutputStream(newFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("◎Upload File◎  " + newFile.getAbsolutePath());
        return prefix + path + fileName;
    }

    @Override
    public void delete(String filePath) {
        String uploadPath = uploadProperties.getPath();
        String prefix     = uploadProperties.getPrefix();
        if (filePath.startsWith(prefix)) {
            filePath = filePath.replace(prefix, "");
        }
        File file = new File(uploadPath + filePath);
        log.info("◎Delete File◎  " + file.getAbsolutePath());
        try {
            FileUtils.forceDelete(file);
        } catch (IOException ignore) {
            log.warn("◎Delete File 失败◎  " + file.getAbsolutePath());
        }
    }

    /**
     * 得到一个新名字
     *
     * @param name 原来的名字
     * @return
     */
    private String generateNewName(String name) {
        String extension = FilenameUtils.getExtension(name);
        if (StringUtils.isBlank(extension)) {
            RandomStringUtils.randomAlphanumeric(32);
        }
        return RandomStringUtils.randomAlphanumeric(32) + "." + extension;
    }
}
