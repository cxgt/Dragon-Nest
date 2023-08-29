package com.learningRoad.controller;

import com.learningRoad.domain.R;
import com.learningRoad.domain.SysFile;
import com.learningRoad.utils.CosFileUpload;
import com.learningRoad.utils.file.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @author chenxin
 * @date 2023/06/19 10:49
 */
@RestController
@RequestMapping("/CosFileUtil")
public class CosFileController {
    @Autowired
    private CosFileUpload cosFileUpload;
    /**
     * 文件上传请求
     */
    @PostMapping("upload")
    public R upload(MultipartFile file)
    {
        String fileName = file.getOriginalFilename();
        Map map;
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
        String url = String.valueOf(map.get("filePath"));
        SysFile sysFile = new SysFile();
        sysFile.setName(FileUtils.getName(url));
        sysFile.setUrl(url);
        return R.ok(sysFile);
    }
}
