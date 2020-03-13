package com.wcj.utils.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.wcj.utils.pojo.vo.BaseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: create by wcj
 * @date: 2019/12/3 0003
 * @time: 下午 16:34
 * @Description: oss对象服务上传的工具
 */
@Component
public class File2OssUtils {

    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${ali.accessKeyId}")
    private String accessKeyId;

    @Value("${ali.accessKeySecret}")
    private String accessKeySecret;

    @Value("${oss.bucketName}")
    private String bucketName;

    @Value("${oss.url}")
    private String url;

    /**
     * 上传图片到oss
     *
     * @param file
     */
    public BaseResult<String> file2Oss(MultipartFile file, String folder) {
        if (file == null){
            return BaseResult.failMsg("文件为空");
        }
        String filename = file.getOriginalFilename();
        if (StringUtils.isBlank(filename)){
            return BaseResult.failMsg("文件为空");
        }
        String substring = filename.substring(filename.lastIndexOf("."));
        String fileName = folder + UUID.randomUUID() + substring;
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            ossClient.putObject(bucketName, fileName, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 关闭OSSClient。
        ossClient.shutdown();
        String fileUrl = url + fileName;
        return BaseResult.successData(fileUrl);
    }
}
