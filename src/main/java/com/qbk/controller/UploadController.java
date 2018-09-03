package com.qbk.controller;



import com.qbk.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @Auther: quboka
 * @Date: 2018/9/3 14:02
 * @Description: 上传
 */
@Slf4j
@Controller
public class UploadController {


    @RequestMapping("/upload")
    @ResponseBody
    public Map<String ,Object> upload(
            @RequestParam("file") MultipartFile[] files ,
            HttpServletRequest request
    ) throws IOException {

        for (MultipartFile file : files) {
            if(file == null && !file.isEmpty()){
                log.info("请选择上传文件");
                continue;
            }
            String originalFilename = file.getOriginalFilename();
            log.info("原名称："+originalFilename);
            String uri = "/images/";
            String basePath = request.getServletContext().getRealPath(uri);
            String uuid =  UUID.randomUUID().toString().replaceAll("\\-", "");
            String suffix = "." + originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            StringBuffer sb = new StringBuffer();
            sb.append(basePath).append(uuid).append(suffix);

            File filepath = new File(sb.toString());
            //判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            //文件绝对路径
            log.info("文件绝对路径：" + filepath.getPath());

            //将上传文件保存到一个目标文件当中
            file.transferTo(filepath);

            String fileUrl = request.getContextPath() + uri + uuid +suffix;
            log.info("访问url：" + fileUrl);
        }

        return ResultUtils.getResult(true,"....");

    }









}
