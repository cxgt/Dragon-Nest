package com.learningRoad.utils;

import com.alibaba.fastjson.JSONObject;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * @ClassName CosFileUpload.java
 * @version 1.0.0
 * @Description COS文件上传服务
 * @author wangyx
 * @date 2023/5/5
 */
@Component
public class CosFileUpload {
    private static final Logger log = LoggerFactory.getLogger(CosFileUpload.class);
    @Value("${cos.bucket}")
    private String bucket;
    @Value("${cos.accessKey}")
    private String accessKey;
    @Value("${cos.secretKey}")
    private String secretKey;
    @Value("${cos.serviceEndpoint}")
    private String serviceEndpoint;
    @Value("${cos.region}")
    private String region;
    @Value("${cos.fileBaseUrl}")
    private String fileBaseUrl;

    public AmazonS3 getConn() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AWSStaticCredentialsProvider awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(credentials);
        ClientConfiguration config = new ClientConfiguration();
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(serviceEndpoint, region);
        AmazonS3 conn = AmazonS3ClientBuilder.standard()
                .withCredentials(awsStaticCredentialsProvider)
                .withClientConfiguration(config.withProtocol(Protocol.HTTP).withSignerOverride("S3SignerType"))
                .withEndpointConfiguration(endpointConfiguration).build();
        return conn;
    }

    public Map upload(String key, ByteArrayInputStream content) throws Exception {
        PutObjectResult result = getConn().putObject(bucket, key, content, new ObjectMetadata());
        log.info("COS文件上传返回：{}", JSONObject.toJSONString(result));
        Map<String, String> map = new HashMap<>(8);
        map.put("filePath", fileBaseUrl + key);
        return map;
    }
}
