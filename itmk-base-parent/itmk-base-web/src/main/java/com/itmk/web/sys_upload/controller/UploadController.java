package com.itmk.web.sys_upload.controller;

import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
        String fileName = UUID.randomUUID().toString().substring(0, 2) + System.currentTimeMillis() + suffix;

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

    @DeleteMapping ("/del/{filename}")
    public ResultVo updateImage(@PathVariable String filename) {
        // 删除原有图片
        File oldFile = new File(basePath + File.separator + filename);
        System.out.println(oldFile);
        // 如果文件不存在，返回相应的错误消息
        if (!oldFile.exists()) {
            return ResultUtils.error("图片不存在");
        }

        // 尝试删除文件
        if (oldFile.delete()) {
            // 如果删除成功，返回成功消息
            return ResultUtils.success("图片已删除");
        } else {
            // 如果删除失败，返回相应的错误消息
            return ResultUtils.error("无法删除原有图片");
        }

    }
}
