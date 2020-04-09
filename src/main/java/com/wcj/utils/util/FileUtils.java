package com.wcj.utils.util;

import com.wcj.admin.pojo.entity.BaseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: create by wcj
 * @date: 2019/12/3 0003
 * @time: 下午 16:34
 * @Description: 文件上传下载
 */
@Component
public class FileUtils {

    @Value("${file.path}")
    private String filePath;

    @Value("${file.server}")
    private String fileServer;

    /**
     * 文件上传
     *
     * @param file
     */
    public BaseResult<String> fileUpload(MultipartFile file) {
        if (file == null) {
            return BaseResult.failMsg("文件为空");
        }
        String filename = file.getOriginalFilename();
        if (StringUtils.isBlank(filename)) {
            return BaseResult.failMsg("文件为空");
        }
        String substring = filename.substring(filename.lastIndexOf("."));
        String fileName = UUID.randomUUID() + substring;
        try {
            File path = new File(filePath);
            if (!path.exists()) {
                path.mkdirs();
            }
            file.transferTo(new File(path, fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BaseResult.successData(fileServer + fileName);
    }


    public File getFile(String name){
        File file = new File(filePath + name);
        if (file.exists()){
            return file;
        }
        return null;
    }
}
