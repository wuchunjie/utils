package com.wcj.utils.controller;

import com.wcj.utils.pojo.vo.BaseResult;
import com.wcj.utils.util.File2OssUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: create by wcj
 * @date: 2020/3/13 0013
 * @time: 下午 13:08
 * @Description:
 */
@RestController
@Api(tags = "文件上传")
@RequestMapping("/fs/file")
public class FileController {

    @Value("${oss.images}")
    private String images;

    @Value("${oss.vocality}")
    private String vocality;

    @Value("${oss.video}")
    private String video;

    @Resource
    private File2OssUtils file2OssUtils;

    /**
     * 图片上传
     *
     * @param file
     * @return
     */
    @ApiOperation("图片上传")
    @PostMapping("/imageUpload")
    public BaseResult<String> imageUpload(MultipartFile file) {
        return file2OssUtils.file2Oss(file, images);
    }

    /**
     * 音频上传
     *
     * @param file
     * @return
     */
    @ApiOperation("音频上传")
    @PostMapping("/vocalityUpload")
    public BaseResult<String> vocalityUpload(MultipartFile file) {
        return file2OssUtils.file2Oss(file, vocality);
    }

    /**
     * 视频上传
     *
     * @param file
     * @return
     */
    @ApiOperation("视频上传")
    @PostMapping("/videoUpload")
    public BaseResult<String> videoUpload(MultipartFile file) {
        return file2OssUtils.file2Oss(file, video);
    }
}
