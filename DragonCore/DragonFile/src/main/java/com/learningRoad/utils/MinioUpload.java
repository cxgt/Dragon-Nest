package com.learningRoad.utils;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MinioUpload.java
 * @version 1.0.0
 * @Description minio文件上传
 * @author wangyx
 * @date 2023/5/4
 */
@Component
public class MinioUpload {
    private static final Logger log = LoggerFactory.getLogger(MinioUpload.class);
    @Value("${minio.url}")
    private String url;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Value("${minio.bucketName}")
    private String bucketName;


    /**
     * 初始化连接
     *
     * @return
     * @throws Exception
     */
    public MinioClient getMinioClient() {
        return MinioClient.builder().endpoint(url).credentials(accessKey, secretKey).build();
    }

    /**
     * 根据存储桶的名称判断桶是否存在，不存在就创建
     *
     * @param bucketName
     * @throws Exception
     */
    public void isExistBucketName(String bucketName) throws Exception {
        boolean isExist = getMinioClient().bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!isExist) {
            getMinioClient().makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            log.info(bucketName + "创建成功");
        }
    }

    /**
     * Minio远程文件上传 上传对象是MultipartFile
     *
     * @param file     文件实体
     * @param fileName 修饰过的文件名 非源文件名
     * @return
     */
    public Map minioUpload(MultipartFile file, String fileName, String bucketName) throws Exception {
            isExistBucketName(bucketName);
            // fileName为空，说明要使用源文件名上传
            if (fileName == null) {
                fileName = file.getOriginalFilename();
                fileName = fileName.replaceAll(" ", "_");
            }
            getMinioClient().putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(file.getInputStream(),
                                    file.getSize(),
                                    -1)
                            .contentType(file.getContentType())
                            .build());
            log.info("成功上传文件 " + fileName + " 至 " + bucketName);
            Map<String, String> result = new HashMap<>(8);
            result.put("filePath", url + "/" + bucketName + "/" + fileName);
            return result;
    }
}