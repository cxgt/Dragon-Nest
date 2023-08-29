package com.learningRoad.controller;


import com.learningRoad.domain.R;
import com.learningRoad.utils.CosFileUpload;
import com.learningRoad.utils.MinioUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @ClassName FileUtilController.java
 * @version 1.0.0
 * @Description 文件上传
 * @author wangyx
 * @date 2023/5/5
 */
@RestController
@RequestMapping("/fileUtil")
public class FileUtilController {

    @Value("${minio.bucketName}")
    private String bucketName;

    @Value("${fileUploadConfigParam:minio}")
    private String fileUploadConfigParam;

    @Autowired
    private MinioUpload minioService;

    @Autowired
    private CosFileUpload cosFileUpload;

    @PostMapping("/upload")
    public R upload(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        Map map;
        if ("minio".equals(fileUploadConfigParam)) {
            try {
                map = minioService.minioUpload(file, fileName, bucketName);
            } catch (Exception e) {
                e.printStackTrace();
                return R.fail(e);
            }
        } else {
            ByteArrayInputStream input = null;
            try {
                input = new ByteArrayInputStream(file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                return R.fail(e);
            }
            try {
                map = cosFileUpload.upload(fileName, input);
            } catch (Exception e) {
                e.printStackTrace();
                return R.fail(e);
            }
        }
        return R.ok(map);
    }
}