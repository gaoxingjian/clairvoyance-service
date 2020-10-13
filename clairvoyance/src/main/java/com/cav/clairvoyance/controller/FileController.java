package com.cav.clairvoyance.controller;

import com.cav.clairvoyance.exception.BusinessException;
import com.cav.clairvoyance.exception.code.BaseResponseCode;
import com.cav.clairvoyance.service.FileService;
import com.cav.clairvoyance.utils.DataResult;
import com.cav.clairvoyance.utils.FileUtil;
import com.cav.clairvoyance.utils.ZipUtil;
import io.swagger.annotations.Api;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;


@Api(tags = "文件")
@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;
    private static String fileUploadRootDir = null;

    @Value("${file.upload.root.dir.windows}")
    String fileUploadRootDirWindows;

    @Value("${file.upload.root.dir.mac}")
    String fileUploadRootDirMac;

    @Value("${file.upload.root.dir.linux}")
    String fileUploadRootDirLinux;


    @PostConstruct
    public void initFileRepository() {

        // 判断文件夹是否存在，不存在就创建
        String osName = System.getProperty("os.name");

        if (osName.startsWith("Mac OS")) {

            fileUploadRootDir = fileUploadRootDirMac;
        } else if (osName.startsWith("Windows")) {

            fileUploadRootDir = fileUploadRootDirWindows;
        } else {

            fileUploadRootDir = fileUploadRootDirLinux;
        }
        FileUtil.makeDir(fileUploadRootDir);
    }


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public DataResult fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
        DataResult result = DataResult.success();
        // String uploadPath = request.getSession().getServletContext().getRealPath("/files");
        String uploadPath = new File("files").getAbsolutePath();
        System.out.println(uploadPath);
        result.setData(fileService.singleUpload(file, uploadPath));
        return result;
    }

    @GetMapping(value = "/download/{id}")
    public void download(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // String basePath = request.getSession().getServletContext().getRealPath("/files");
        String basePath = new File("files").getAbsolutePath();
        String reportsPath = basePath + File.separator + id + File.separator + "detection_report_" + id;
        File reports = new File(reportsPath);

        if (!reports.exists() || reports.listFiles().length == 0)
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);

        File zipFile = new File(reportsPath + ".zip");
        // 压缩报告
        ZipUtil.zipDir(zipFile.getAbsolutePath(), reportsPath);

        // 读取文件内容
        FileInputStream is = new FileInputStream(zipFile);
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(zipFile.getName(),"UTF-8"));
        ServletOutputStream os = response.getOutputStream();
        IOUtils.copy(is, os);
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(os);

        //return DataResult.success();
    }
}
