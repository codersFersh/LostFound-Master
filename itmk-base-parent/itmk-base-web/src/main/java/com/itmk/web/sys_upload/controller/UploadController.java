package com.itmk.web.sys_upload.controller;

import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Value("${upload.path}")
    private String basePath; // 文件上传基本路径

    @PostMapping("/img")
    public ResultVo uploadImage(@RequestParam("file") MultipartFile file, HttpServletRequest req) {
        if (file.isEmpty()) {
            return ResultUtils.error("无法获取该图片");
        }

        // 获取图片文件名和后缀
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 使用UUID和时间戳给图片重新命名,防止图片覆盖替换
        String fileName = UUID.randomUUID().toString() + System.currentTimeMillis() + suffix;

        // 创建目录
        File dir = new File(basePath);
        // 判断目录是否存在
        if (!dir.exists()) {
            // 不存在则新建文件夹
            if (!dir.mkdirs()) {
                return ResultUtils.error("无法创建文件夹");
            }
        }

        // 保存文件
        try {
            file.transferTo(new File(basePath + File.separator + fileName));
            String url = req.getScheme() +"://" +req.getServerName() + ":" + req.getServerPort()+"/img/" +fileName;
            return ResultUtils.success("图片已上传", 200, url);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultUtils.error("上传失败");
        }
    }

    @PutMapping("/img/{filename}")
    public ResultVo updateImage(@PathVariable String filename, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResultUtils.error("无法获取该图片");
        }

        // 删除原有图片
        File oldFile = new File(basePath + File.separator + filename);
        if (oldFile.exists() && !oldFile.delete()) {
            return ResultUtils.error("无法删除原有图片");
        }

        // 保存新图片
        try {
            file.transferTo(new File(basePath + File.separator + filename));
            return ResultUtils.success("图片已更新", filename);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultUtils.error("更新失败");
        }
    }
}
