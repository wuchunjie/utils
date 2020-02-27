package com.wcj.utils.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
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

    @Value("${oss.accessKeyId}")
    private String accessKeyId;

    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${oss.bucketName}")
    private String bucketName;

    @Value("${oss.url}")
    private String url;

    @Value("${oss.images}")
    private String images;

    /**
     * 上传图片到oss
     *
     * @param file
     */
    public String file2Oss(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = images + UUID.randomUUID() + "_" + System.currentTimeMillis() + substring;
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            ossClient.putObject(bucketName, fileName, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 关闭OSSClient。
        ossClient.shutdown();
        return url + fileName;
    }

    /**
     * 上传图片到oss
     *
     * @param inputStream
     */
    public String file2Oss(InputStream inputStream, String originalFilename) {
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = images + UUID.randomUUID() + "_" + System.currentTimeMillis() + substring;
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, fileName, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
        return url + fileName;
    }
}
