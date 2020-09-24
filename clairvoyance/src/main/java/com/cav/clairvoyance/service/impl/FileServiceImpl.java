package com.cav.clairvoyance.service.impl;

import com.cav.clairvoyance.domain.Result;
import com.cav.clairvoyance.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;



@Service
public class FileServiceImpl implements FileService {

    String winPath = "E:\\workspace\\clairvoyance-service\\clairvoyance\\files\\";
    String linuxPath = "/home/mingliang/files";
    @Override
    public Result upload(MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new Exception("未选择需上传的待测文件");
        }
        //String filePath = new File("files").getAbsolutePath();
        String filePath = new File("files").getAbsolutePath();
        System.out.println(filePath);
        File fileUpload = new File(filePath);
        if (fileUpload.exists()) {
            delete(fileUpload);
        }
        if (!fileUpload.exists()) {
            fileUpload.mkdirs();
        }
        //delete(fileUpload);
        // 得到上传的文件
        fileUpload = new File(filePath, System.currentTimeMillis()+file.getOriginalFilename());
        if (fileUpload.exists()) {
            throw new Exception("上传的文件已存在");
        }

        try {
            file.transferTo(fileUpload); // 上传文件
            System.out.println(fileUpload.getName());
            String extName = fileUpload.getName().split("\\.")[1];//得到文件的后缀名
            if (extName.equals("zip")) { //如果是压缩文件
                ZipFile zipFile = new ZipFile(fileUpload);//创建压缩文件对象
                //开始解压
                Enumeration<?> entries = zipFile.entries();
                while (entries.hasMoreElements()) { // 便利压缩文件中的实例对象
                    ZipEntry entry = (ZipEntry) entries.nextElement();
                    // 如果是文件夹，就创建个文件夹
                    if (entry.isDirectory()) {  //如果压缩文件中的实例是个目录
                        String dirPath = filePath +"/"+ entry.getName();
                        fileUpload.mkdirs();
                    } else {
                        // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                        File targetFile = new File(filePath +"/"+ entry.getName());
                        // 保证这个文件的父文件夹必须要存在
                        if (!targetFile.getParentFile().exists()) {
                            targetFile.getParentFile().mkdirs();
                        }
                        targetFile.createNewFile();
                        // 将压缩文件内容写入到这个文件中
                        InputStream is = zipFile.getInputStream(entry);  // 得到压缩文件的输入流
                        FileOutputStream fos = new FileOutputStream(targetFile); // 得到目标文件的输出流
                        int len;
                        byte[] buf = new byte[1024];
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                        }
                        // 关流顺序，先打开的后关闭

                        fos.close();
                        is.close();
                    }
                }
                // 删除zip
                zipFile.close();
            }
            fileUpload.delete();
            return new Result(200, "上传成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(500, "上传失败");

    }

    private void delete(File directory) {
        if (!directory.isDirectory()){
            directory.delete();
        } else{
            File [] files = directory.listFiles();
            // 空文件夹
            if (files.length == 0){
                directory.delete();
                System.out.println("删除" + directory.getAbsolutePath());
                return;
            }

            // 删除子文件夹和子文件
            for (File file : files){
                if (file.isDirectory()){
                    delete(file);
                } else {
                    file.delete();
                    System.out.println("删除" + file.getAbsolutePath());
                }
            }

             //删除文件夹本身
            directory.delete();
            System.out.println("删除" + directory.getAbsolutePath());
        }
    }
}
